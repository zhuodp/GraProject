package com.zhuodp.graduationproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;


import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.MovieListAdapter;
import com.zhuodp.graduationproject.entity.Movie;
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


    private List<Movie> mTestMovieList;

    private String mTestPicUrl = "https://b-ssl.duitang.com/uploads/item/201605/11/20160511103527_yzHMj.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        mTestMovieList = new ArrayList<Movie>();
        getMovie(this);
    }

    //初始化列表
    private void initRecyclerView(){
        mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mMovieListAdapter = new MovieListAdapter(this,mTestMovieList);
        mMovieRecyclerView.setLayoutManager(mLayoutManager);
        int interval = (int)getResources().getDimension(R.dimen.app_5dp);
        mMovieRecyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this,interval));
        mMovieRecyclerView.setAdapter(mMovieListAdapter);
    }


    //查找电影列表
    public void getMovie(Context context) {
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "电影列表获取成功", Toast.LENGTH_SHORT).show();
                    mTestMovieList = list;
                    initRecyclerView();
                } else {
                    Toast.makeText(context, "列表获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
