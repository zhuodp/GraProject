package com.zhuodp.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.FilterAdapter;
import com.zhuodp.graduationproject.adapter.MovieListAdapter;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.helper.RecyclerViewItemClickSupport;
import com.zhuodp.graduationproject.helper.StaggeredDividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 *  MovieListActivity ： 用来展示影片库的Activity
 *
 */
public class MovieListActivity extends AppBaseActivity {

    private String TAG = ""; //指示当前点击的是哪一组分类

    private String mSelectedMovieType = "全部";
    private String mSelectedTime = "全部";
    private String mSelectedCountry = "全部";

    private String mSelectionType=Constant.DATA_MOVIE_SELECT_NONE; //如热门等
    private String mSearchKey = null;//关键字搜索

    @BindView(R.id.recyclerview_movie_list)
    RecyclerView mMovieRecyclerView;

    @BindView(R.id.gridview_sort_list)
    GridView mGridViewSortMenu;

    @BindView(R.id.tv_sorted_by_type)
    TextView mTvSortByType;

    @BindView(R.id.tv_sorted_by_time)
    TextView mTvSortByTime;

    @BindView(R.id.tv_sorted_by_country)
    TextView mTvSortByCountry;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieListAdapter mMovieListAdapter;
    private List<Movie> mMovieList = new ArrayList<>();

    //所有的筛选列表共用一个Adapter
    private FilterAdapter mFilterAdapter;

    private String[] mType = new String[]{"全部","热门","剧情","动漫","喜剧","动作","科幻","恐怖","爱情","励志","传记"};
    private String[] mTime = new String[]{"全部","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2003","2002","2001","2000","2000年之前"};
    private String[] mCountry = new String[]{"全部","中国","韩国","日本","美国","英国","德国","法国","意大利"};






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        initGridView();
        getIntentData();//获取Intent中的数据
        getMovie(this,true);
    }

    public void onSortedByType(View view){
        if (mGridViewSortMenu.getVisibility()==View.VISIBLE && TAG.equals("TYPE")){
            mGridViewSortMenu.setVisibility(View.GONE);
        }else {
            showGridView("TYPE");
        }
    }

    public void onSortedByTime(View view){
        if (mGridViewSortMenu.getVisibility()==View.VISIBLE && TAG.equals("TIME")){
            mGridViewSortMenu.setVisibility(View.GONE);
        }else {
            showGridView("TIME");
        }
    }

    public void onSortedByCountry(View view){
        if (mGridViewSortMenu.getVisibility()==View.VISIBLE && TAG.equals("COUNTRY")){
            mGridViewSortMenu.setVisibility(View.GONE);
        }else {
            showGridView("COUNTRY");
        }
    }



    //参数为true时则为初始化RecyclerView ，参数为false时则为刷新UI
    private void initRecyclerView(boolean isFistTime){
        if (isFistTime){
            mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
            mMovieListAdapter = new MovieListAdapter(this, mMovieList);
            mMovieRecyclerView.setLayoutManager(mLayoutManager);
            int interval = (int)getResources().getDimension(R.dimen.app_5dp);
            mMovieRecyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this,interval));
            RecyclerViewItemClickSupport.addTo(mMovieRecyclerView).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    String movieObjectId = mMovieList.get(position).getObjectId();
                    String movieName = mMovieList.get(position).getMovieName();
                    String moviePicUrl = mMovieList.get(position).getPicUrl();
                    String[] movieActors = mMovieList.get(position).getActors();
                    String moviePublishDate = mMovieList.get(position).getPublishedDate();
                    String movieIntro = mMovieList.get(position).getIntroduction();
                    Log.e("MovieListAc",movieName);
                    Log.e("MovieListAc",moviePicUrl);
                    Log.e("MovieListAc",movieActors[0]);
                    Log.e("MovieListAc",moviePublishDate);
                    Log.e("MovieListAc",movieIntro);


                    Intent intent = new Intent(MovieListActivity.this,VideoPlayerActivity.class);
                    intent.putExtra(Constant.DATA_MOVIE_OBJECT_ID,movieObjectId);
                    intent.putExtra(Constant.DATA_MOVIE_NAME,movieName);
                    intent.putExtra(Constant.DATA_MOVIE_URL,Constant.MOVIE_URL_TEST);//暂时固定死视频的URL TODO 在数据表中加入视频url连接
                    intent.putExtra(Constant.DATA_USER_PIC_URL,moviePicUrl);
                    intent.putExtra(Constant.DATA_MOVIE_ACTORS,movieActors);
                    intent.putExtra(Constant.DATA_MOVIE_PUBLISH_DATE,moviePublishDate);
                    intent.putExtra(Constant.DATA_MOVIE_INTRO,movieIntro);
                    startActivity(intent);
                }
            });
            mMovieRecyclerView.setAdapter(mMovieListAdapter);
        }else{
            mMovieListAdapter.notifyDataSetChanged();
        }

    }

    //查找电影列表 (判断是否进行了分类，筛选出合适的电影)
    public void getMovie(Context context,boolean isFirstTime) {
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        //缓存策略，先尝试从缓存中获取查询结果，无果时再从网络获取
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //不是热门则显示全部电影
        if (!mSelectionType.equals(Constant.DATA_MOVIE_SELECT_NONE)){
            bmobQuery.addWhereEqualTo("selectType",mSelectionType);
            Log.e("MovieList","是热门");
        }
        if (!mSelectedCountry.equals("全部")){//判断是否根据国家进行筛选
            bmobQuery.addWhereEqualTo("country", mSelectedCountry);
            Log.e("MovieList","国家为"+ mSelectedCountry);
        }
        //判断是否根据时间进行筛选
        if (!mSelectedTime.equals("全部")){
            if (!mSelectedTime.equals("2000年之前")){
                bmobQuery.addWhereEqualTo("publishedDate",mSelectedTime);
            }else{
                bmobQuery.addWhereLessThan("publishedDate","2000");
            }
        }
        //判断是否根据影片类型进行筛选
        if (!mSelectedMovieType.equals("全部")){
            bmobQuery.addWhereEqualTo("type",mSelectedMovieType);
            Log.e("MovieList","类型为"+mSelectedMovieType);
        }
        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "电影列表获取成功", Toast.LENGTH_SHORT).show();
                    Log.e("MovieList","获取到的列表长度为"+list.size());
                    //采用下面的方式更新数据集，保证mMovieList的内存地址不变，否则Adapter的notifyDataSetChanged方法会失效
                    mMovieList.clear();
                    if (mSearchKey!=null){
                        //从搜索栏跳转过来则显示筛选搜索的内容
                        Log.e("MovieList","根据搜索关键词筛选后，列表长度为"+list.size());
                        for (int i = 0 ;i<list.size();i++){
                            if ((list.get(i).getMovieName().contains(mSearchKey))){  //模糊搜索匹配
                                mMovieList.add(list.get(i));
                            }
                        }
                    }else{
                        mMovieList.addAll(list);
                    }
                    initRecyclerView(isFirstTime);
                } else {
                    Toast.makeText(context, "列表获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent.getStringExtra(Constant.ACTION_MOVIE_SELECT)!=null){
            mSelectionType =intent.getStringExtra(Constant.ACTION_MOVIE_SELECT);
        };
        if (intent.getStringExtra(Constant.ACTION_MOVIE_SEARCH )!= null) {
            mSearchKey = intent.getStringExtra(Constant.ACTION_MOVIE_SEARCH);
        }
        Log.e("debug",mSelectionType);
    }

    private void initGridView(){

    }

    private void showGridView(String which){
        TextView viewToUpdate;
        String[] dataSet;

        mGridViewSortMenu.setVisibility(View.GONE);
        if (which.equals("TYPE")){
            TAG = "TYPE";
            dataSet = mType;
            viewToUpdate = mTvSortByType;
        }else if(which.equals("TIME")){
            TAG = "TIME";
            dataSet = mTime;
            viewToUpdate = mTvSortByTime;
        }else{
            TAG = "COUNTRY";
            dataSet = mCountry;
            viewToUpdate = mTvSortByCountry;
        }

        mFilterAdapter =new FilterAdapter(dataSet,getBaseContext());
        mGridViewSortMenu.setAdapter(mFilterAdapter);
        mGridViewSortMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewToUpdate.setText(dataSet[position]);
                mGridViewSortMenu.setVisibility(View.GONE);
                if (which.equals("TYPE")){
                    mSelectedMovieType = dataSet[position];
                    Log.e("点击事件","设置电影类型为"+dataSet[position]);
                }
                if (which.equals("TIME")){
                    mSelectedTime = dataSet[position];
                    Log.e("点击事件","设置时间为"+dataSet[position]);
                }
                if (which.equals("COUNTRY")){
                    mSelectedCountry = dataSet[position];
                    Log.e("点击事件","设置国家为"+dataSet[position]);
                }
                //重新获取匹配的列表（更新UI）
                getMovie(getBaseContext(),false);
            }
        });
        mGridViewSortMenu.setVisibility(View.VISIBLE);
        mGridViewSortMenu.bringToFront();
    }








    //处理筛选栏变化结果，更新UI
    public void handleMessage(Message msg){
        switch (msg.what){
            default:
                mMovieListAdapter.notifyDataSetChanged();
                break;
        }
    }

    //封装的Handler用于UI更新，并且防止内存泄漏
    private static class MyHandler extends Handler{

        private final WeakReference<MovieListActivity> movieListActivityWeakReference;


        private MyHandler(MovieListActivity activity) {
            this.movieListActivityWeakReference = new WeakReference<MovieListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MovieListActivity activity = movieListActivityWeakReference.get();
            if (activity == null) return ;
            switch (msg.what){
                default:

                    break;
            }
        }

    }



}
