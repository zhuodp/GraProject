package com.zhuodp.graduationproject.utils.LruCache;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by 74021 on 2018/5/15.
 */
//图片压缩
public class ImageResizer {
    private static final String TAG = "ImageResizer";
    public ImageResizer(){

    }
    //从资源加载
    public Bitmap decodeSampleBitmapFromResource(Resources res,int resId ,int reqWidth,int reqHeight){
        //设置inJustedDecodeBounds = true 表示先不加载图片
        final BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds =true;//只读取图片，不加载到内存中
        BitmapFactory.decodeResource(res,resId,options);
        //调用方法计算合适的inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //inJustedDecodeBounds设置为false时真正开始加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);

    }
    //从文件加载(特指从缓存目录加载)
    public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth ,int reqHeight){
        //设置inJustDecodeBounds = true 表示先不加载图片
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        //调用方法计算合适的inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //inJustDecodeBounds设置为false ，真正开始加载图片
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }
    //从手机本地目录加载
    public Bitmap decodeSampleBitmapFromFilePath(String filePath,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeFile(filePath,options);
        //调用方法甲酸合适的inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //inJustDecodeBounds设置为false，真正开始加载图片
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeFile(filePath,options);
    }

    //计算BitmapFactory的inSimpleSize的方法
    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        if (reqWidth==0 || reqHeight == 0){
            return 1;
        }
        //获取图片的原生高宽
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG,"origin,width= "+width+"  height= "+height);
        int inSampleSize = 1;

        //如果原生高宽大于请求的高宽，那么将原生的宽和高都设为原来的一半
        if(height>reqHeight || width >reqHeight){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            //Calculate the largest inSampleSize value this is a power of 2 and
            //keep both height and width larger than the requested height and width
            while ((halfHeight/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
                inSampleSize *=2;
            }
        }

        Log.d(TAG,"sampleSize: "+ inSampleSize);
        return inSampleSize;
    }
}
