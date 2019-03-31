package com.zhuodp.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.MovieListAdapter;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.helper.RecyclerViewItemClickSupport;
import com.zhuodp.graduationproject.helper.StaggeredDividerItemDecoration;

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

    @BindView(R.id.recyclerview_movie_list)
    RecyclerView mMovieRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieListAdapter mMovieListAdapter;
    private List<Movie> mMovieList;

    private String mSelectionType=Constant.DATA_MOVIE_TYPE_NONE;
    private String mSearchKey = null;

    private String mTestPicUrl = "https://b-ssl.duitang.com/uploads/item/201605/11/20160511103527_yzHMj.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        mMovieList = new ArrayList<Movie>();
        Intent intent = getIntent();
        if (intent.getStringExtra(Constant.ACTION_MOVIE_SELECT)!=null){
            mSelectionType =intent.getStringExtra(Constant.ACTION_MOVIE_SELECT);
        };
        if (intent.getStringExtra(Constant.ACTION_MOVIE_SEARCH )!= null) {
            mSearchKey = intent.getStringExtra(Constant.ACTION_MOVIE_SEARCH);
        }
        Log.e("debug",mSelectionType);
        getMovie(this);

    }

    //初始化列表
    private void initRecyclerView(){
        mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mMovieListAdapter = new MovieListAdapter(this, mMovieList);
        mMovieRecyclerView.setLayoutManager(mLayoutManager);
        int interval = (int)getResources().getDimension(R.dimen.app_5dp);
        mMovieRecyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this,interval));
        RecyclerViewItemClickSupport.addTo(mMovieRecyclerView).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
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
    }


    //查找电影列表
    public void getMovie(Context context) {
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        if (!mSelectionType.equals(Constant.DATA_MOVIE_TYPE_NONE)){
            bmobQuery.addWhereEqualTo("selectType",mSelectionType);
        }
        if (mSearchKey!=null){
            bmobQuery.addWhereContains("movieName",mSearchKey);
        }
        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "电影列表获取成功", Toast.LENGTH_SHORT).show();
                    mMovieList = list;
                    initRecyclerView();
                } else {
                    Toast.makeText(context, "列表获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
