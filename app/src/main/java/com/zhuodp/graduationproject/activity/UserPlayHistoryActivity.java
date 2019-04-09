package com.zhuodp.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.CollectionListAdapter;
import com.zhuodp.graduationproject.adapter.HistoryListAdapter;
import com.zhuodp.graduationproject.entity.HistoryItem;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.helper.RecyclerViewItemClickSupport;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;
import com.zhuodp.graduationproject.utils.view.CircleImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class UserPlayHistoryActivity extends AppBaseActivity {

    private LinkedList<HistoryItem> mUserHistoryList;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistoryListAdapter mHistoryListAdapter;

    @BindView(R.id.iv_user_pic_user_history_activity)
    CircleImageView mIvUserPic;

    @BindView(R.id.tv_user_name_history_activity)
    TextView mTvUserName;

    @BindView(R.id.recyclerview_history_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        initRecyclerView();
    }


    //初始化列表
    private void initRecyclerView() {
        mUserHistoryList = BmobUtil.getCurrentUser().getHistory();
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        //mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

        mHistoryListAdapter = new HistoryListAdapter(this, mUserHistoryList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
                bmobQuery.getObject(mUserHistoryList.get(position).getHistoryMovieId(), new QueryListener<Movie>() {
                    @Override
                    public void done(Movie movie, BmobException e) {
                        String movieObjectId = movie.getObjectId();
                        String movieName = movie.getMovieName();
                        String moviePicUrl = movie.getPicUrl();
                        String[] movieActors = movie.getActors();
                        String moviePublishDate = movie.getPublishedDate();
                        String movieIntro = movie.getIntroduction();
                        Log.e("MovieListAc", movieName);
                        Log.e("MovieListAc", moviePicUrl);
                        Log.e("MovieListAc", movieActors[0]);
                        Log.e("MovieListAc", moviePublishDate);
                        Log.e("MovieListAc", movieIntro);


                        Intent intent = new Intent(UserPlayHistoryActivity.this, VideoPlayerActivity.class);
                        intent.putExtra(Constant.DATA_MOVIE_OBJECT_ID, movieObjectId);
                        intent.putExtra(Constant.DATA_MOVIE_NAME, movieName);
                        intent.putExtra(Constant.DATA_MOVIE_URL, Constant.MOVIE_URL_TEST);//暂时固定死视频的URL TODO 在数据表中加入视频url连接
                        intent.putExtra(Constant.DATA_USER_PIC_URL, moviePicUrl);
                        intent.putExtra(Constant.DATA_MOVIE_ACTORS, movieActors);
                        intent.putExtra(Constant.DATA_MOVIE_PUBLISH_DATE, moviePublishDate);
                        intent.putExtra(Constant.DATA_MOVIE_INTRO, movieIntro);
                        startActivity(intent);
                    }
                });

            }
        });
        mRecyclerView.setAdapter(mHistoryListAdapter);

    }

}
