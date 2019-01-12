package com.example.a74021.project1.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.a74021.project1.DiskLruCaChe.DiskLruCache;
import com.example.a74021.project1.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by 74021 on 2018/5/16.
 */

public class ImageLoader {
    /*
    一个优秀的ImageLoader应该具备如下功能：
        ·图片的同步加载
        ·图片的异步加载
        ·图片压缩
        ·内存缓存
        ·磁盘缓存
        ·网络拉取
     */
    private static final String TAG = "ImageLoader";  //用于打印日志的TAG

    public static final int MESSAGE_PORT_RESULT = 1;

    //当前CPU的核心线程数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //线程池THREAD_POOL_EXCUTOR 的实现
    private static final int CORE_POOL_SIZE = CPU_COUNT +1 ; //核心线程数为当前设备的CPU核心数+1
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 +1;//最大容量为CPU核心数的2倍加1
    private static final long KEEP_ALIVE = 10L; //线程闲置超时时长为10秒

    private static final int TAG_KEY_URI = R.id.imageloader_uri; //待补充
    private static final int DISK_CACHE_SIZE = 1024*1024*50;
    private static final int IO_BUFFER_SIZE = 8*2014;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    //定义一个线程池变量用于异步加载图片
    //采用线程池的原因：如果用普通的线程，列表下拉时会产生大量的线程，不利于效率提升
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };


    //Executor接口-----ThreadPooExecutor(核心线程数，最大线程数，非核心线程保活事件，组塞队列，线程工厂)；
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(),
                    sThreadFactory);


    //Handler ，用于在主线程收集异步加载图片的结果
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            LoaderResult result =(LoaderResult)msg.obj;
            ImageView imageView = result.imageView;
            String uri = (String) imageView.getTag();
            if(uri!=null&&uri.equals(result.uri)){
                imageView.setImageBitmap(result.bitmap);
            }else{
                Log.d(TAG,"set image bitmap,but url has changed,ignored!");
            }
        };
    };


    private Context mContext;
    private ImageResizer imageResizer = new ImageResizer();
    private LruCache<String,Bitmap> mMemoryCache;   //内存缓存
    private DiskLruCache mDiskLruCache;             //磁盘缓存

    //**********************************************************构造函数***************************************************************************
    //创建磁盘缓存 （创建前判断磁盘缓存是否已满）
    private ImageLoader(Context context){
        mContext = context.getApplicationContext();
        int maxMemory = (int )(Runtime.getRuntime().maxMemory()/1024);  //最大磁盘缓存
        int cacheSize = maxMemory/8;                                    //最大内存缓存  ，磁盘缓存的容量为50MB ，内存缓存为其1/8
        //获取侧畔缓存对象
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){      //用LruCache<String,Bitmap> 来构建内存缓存mMemoryCache
            @Override
            protected int sizeOf(String key,Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight()/1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(mContext , "bitmap");
        if(!diskCacheDir.exists()){                                 //磁盘缓存用的文件夹不存在则创建文件夹
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir)>DISK_CACHE_SIZE){          //创建磁盘缓存前先做判断，磁盘缓存是否满了
            try{
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public static ImageLoader build (Context context){
        return new ImageLoader(context);
    }                                                               //简单的builder模式

//**************************************************************************************************************************************



//********************************************内存缓存的添加和获取*****************************************************************************
    private void addBitmapToMemoryCache(String key,Bitmap bitmap){   //添加内存缓存，key和bitmap类型， 判断不存在该缓存后使用LruCache的put函数
        if (getBitmapFromMemCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }
    private Bitmap getBitmapFromMemCache(String key){
        return mMemoryCache.get(key);
    }
//**************************************************************************************************************************************************



//*****************************************************异步加载图片**************************************************************************
    public void bindBitmap(final String uri,final ImageView imageView){
        bindBitmap(uri,imageView,0,0);
    }

    public void bindBitmap(final String uri, final  ImageView imageView, final int reqWidth, final int reqHeight){

        imageView.setTag(uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);                            //首先尝试从内存中读取图片
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return ;
        }
        //从磁盘和网络拉取都为耗时操作，需要异步执行
        //线程池的工作模型主要两部分组成，一部分是运行Runnable的Thread对象，另一部分就是阻塞队列
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri,reqWidth,reqHeight);
                if (bitmap!=null){
                        LoaderResult result = new LoaderResult(imageView,uri,bitmap);   //结果类，创房bitMap ，uri等
                        mMainHandler.obtainMessage(MESSAGE_PORT_RESULT,result).sendToTarget();  //异步取得图片后，通过Handler发送回主线程
                    };
                }
            };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }
//*********************************************************************************************************************************


//***************************************同步加载图片*************************************************************
    //依次尝试从 内存、 磁盘、和网络中拉取已经存在的缓存
    public Bitmap loadBitmap(String uri,int reqWidth,int reqHeight){
        Bitmap bitmap =loadBitmapFromMemCache(uri);                                                 //首先从内存中检查内能否加载
        if (bitmap !=null){
            Log.d(TAG,"loadBitmapFromMemCache,url :"+uri);
            return bitmap;
        }
        try{
            bitmap = loadBitmapFromDiskCache(uri,reqWidth,reqHeight);                               //再从磁盘中检查能否加载
            if (bitmap != null){
                Log.d(TAG,"loadBitmapFromDisk,url :"+uri);
                return bitmap;
            }else {
                bitmap = loadBitmapFromHttp(uri,reqWidth,reqHeight);// （耗时，不能在主线程中调用） //最后从网络中拉取图片
                Log.d(TAG,"loadBitmapFromHttp,url: "+uri);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        if (bitmap ==null && !mIsDiskLruCacheCreated){
            Log.w(TAG,"encounter error ,DiskLruCache is not created.");                       //
            try {
                bitmap =downloadBitmapFromUrl(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

//***********************************************内存，磁盘，网络 加载图片的函数************************************************************
    private Bitmap loadBitmapFromMemCache(String url){              //从内存得到图片
        final String key = hashKeyFormUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }
    //从磁盘缓存中加载图片
    private Bitmap loadBitmapFromDiskCache(String url,int reqWidth,int reqHeight) throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG,"load bitmap from UI thread ,it's not recommended!");
        }
        if (mDiskLruCache == null){
            return  null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null){
            FileInputStream fileInputStream = (FileInputStream)snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor =fileInputStream.getFD();
            bitmap = imageResizer.decodeSampleBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
            if (bitmap !=null){
                addBitmapToMemoryCache(key,bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException{    //从网络得到图片
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw  new RuntimeException("cannot visit netWork from UI Thread");
        }
        if (mDiskLruCache == null){
            return null;
        }

        String key = hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = (OutputStream) editor.newOutputStream(DISK_CACHE_INDEX);//newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url,outputStream)){ //如果从网络中下载图片成功
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, reqWidth,reqHeight);
    }

    // 将图片从网络中下载到磁盘
    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try{
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);

            int b;
            while((b = in.read())!=-1){
                out.write(b);
            }
            return true;
        }catch (IOException e){
            Log.e(TAG,"DownloadBitmap failed ." + e);
        }finally {
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            //需要关闭输入输出流
            if (in != null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
        }
        return false;
    }





    private Bitmap downloadBitmapFromUrl(String urlString) throws IOException {
        Bitmap bitmap= null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try{
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            Log.d(TAG,"Error in downloadBitmap :" + e);
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (in != null){
                in.close();
            }


        }
        return bitmap;
    }

    private  String hashKeyFormUrl(String url){
        String cacheKey;
        try{
            final MessageDigest mDigset = MessageDigest.getInstance("MD5");
            mDigset.update(url.getBytes());
            cacheKey = bytesToHexString(mDigset.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);

        }
        return sb.toString();
    }

    public File getDiskCacheDir(Context context, String uniqueName){
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath =context.getCacheDir().getPath();
        }
        return new File(cachePath +File.separator + uniqueName);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private  long  getUsableSpace(File path){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long)stats.getBlockCountLong() *(Long)stats.getAvailableBlocksLong();
    }

    private static class LoaderResult{
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView,String uri,Bitmap bitmap){
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

}
