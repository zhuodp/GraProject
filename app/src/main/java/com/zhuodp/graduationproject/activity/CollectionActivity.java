package com.zhuodp.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.CollectionListAdapter;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.entity.User;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.helper.RecyclerViewItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends AppBaseActivity {

    @BindView(R.id.recyclerview_collection_list)
    RecyclerView mCollectionListRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private CollectionListAdapter mCollectionListAdapter;
    private List<Movie> mMovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 优化UI
        setContentView(R.layout.activity_collection);
        getMovie(getBaseContext());
    }


    //初始化列表
    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        //mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mCollectionListAdapter = new CollectionListAdapter(this, mMovieList);
        mCollectionListRecyclerView.setLayoutManager(mLayoutManager);
        //int interval = (int)getResources().getDimension(R.dimen.app_5dp);
        //mCollectionListRecyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this,interval));
        RecyclerViewItemClickSupport.addTo(mCollectionListRecyclerView).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() {
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


                Intent intent = new Intent(CollectionActivity.this,VideoPlayerActivity.class);
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

        mCollectionListRecyclerView.setAdapter(mCollectionListAdapter);
    }

    //查找电影列表
    public void getMovie(Context context) {
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        User currentUser = BmobUtil.getCurrentUserCache();
        List<String> collectionList = currentUser.getFavorList();
        bmobQuery.addWhereContainedIn("objectId",collectionList);
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

    public void onClick4Back(View view){
        finish();
    }


}
