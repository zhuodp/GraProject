package com.zhuodp.graduationproject.fragment.tab;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.util.RoundAngleImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.MovieListActivity;
import com.zhuodp.graduationproject.activity.VideoPlayerActivity;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.GlideImageLoader;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//TODO Banner实现
public class HotPiontTabFragment extends AppBaseFragment {

    List<Movie> hotPiontMovies = new ArrayList<>();
    List<Movie> latestMovies = new ArrayList<>();
    List<Movie> guessMovies = new ArrayList<>();


    @BindView(R.id.tv_tab_more_hot_point)
    TextView mClickAreaForMoreHotPoint;

    @BindView(R.id.hotpoint_movie_1)
    RoundAngleImageView mIvHotPiontMovie1;

    @BindView(R.id.hotpoint_movie_2)
    RoundAngleImageView mIvHotPiontMovie2;

    @BindView(R.id.hotpoint_movie_3)
    RoundAngleImageView mIvHotPiontMovie3;

    @BindView(R.id.latest_movie_1)
    RoundAngleImageView mIvLatestMovie1;

    @BindView(R.id.latest_movie_2)
    RoundAngleImageView mIvLatestMovie2;

    @BindView(R.id.latest_movie_3)
    RoundAngleImageView mIvLatestMovie3;

    @BindView(R.id.guess_movie_1)
    RoundAngleImageView mIvGuessMovie1;

    @BindView(R.id.guess_movie_2)
    RoundAngleImageView mIvGuessMovie2;

    @BindView(R.id.guess_movie_3)
    RoundAngleImageView mIvGuessMovie3;

    @BindView(R.id.title_hot_movie_1)
    TextView mTvTitleHotPiontMovie1;

    @BindView(R.id.title_hot_movie_2)
    TextView mTvTitleHotPiontMovie2;

    @BindView(R.id.title_hot_movie_3)
    TextView mTvTitleHotPiontMovie3;

    @BindView(R.id.title_latest_movie_1)
    TextView mTvLatestMovie1;

    @BindView(R.id.title_latest_movie_2)
    TextView mTvLatestMovie2;

    @BindView(R.id.title_latest_movie_3)
    TextView mTvLatestMovie3;

    @BindView(R.id.title_guess_movie_1)
    TextView mTvGuessMovie1;

    @BindView(R.id.title_guess_movie_2)
    TextView mTvGuessMovie2;

    @BindView(R.id.title_guess_movie_3)
    TextView mTvGuessMovie3;

    //点击电影封面，进入到视频详情页
    @OnClick({R.id.hotpoint_movie_1,R.id.hotpoint_movie_2,R.id.hotpoint_movie_3,R.id.latest_movie_1,R.id.latest_movie_2,R.id.latest_movie_3})
    public void onHotPiontMovieClick(View view){
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        //TODO 将点击电影的属性附着到Intent上
        int targetMoviePosition = -1;
        List<Movie> targetMovieList = new ArrayList<Movie>();
        switch (view.getId()){
            case R.id.hotpoint_movie_1:
                targetMoviePosition = 0;
                targetMovieList = hotPiontMovies;
                break;
            case R.id.hotpoint_movie_2:
                targetMoviePosition = 1;
                targetMovieList = hotPiontMovies;
                break;
            case R.id.hotpoint_movie_3:
                targetMoviePosition = 2;
                targetMovieList =hotPiontMovies;
                break;
            case R.id.latest_movie_1:
                targetMoviePosition = 0;
                targetMovieList = latestMovies;
                break;
            case R.id.latest_movie_2:
                targetMoviePosition = 1;
                targetMovieList = latestMovies;
                break;
            case R.id.latest_movie_3:
                targetMoviePosition = 2;
                targetMovieList = latestMovies;
                break;
            case R.id.guess_movie_1:
                targetMoviePosition = 0;
                targetMovieList = guessMovies;
                break;
            case R.id.guess_movie_2:
                targetMoviePosition = 1;
                targetMovieList = guessMovies;
                break;
            case R.id.guess_movie_3:
                targetMoviePosition = 2;
                targetMovieList = guessMovies;
                break;
        }
        startPlayerActivity(targetMovieList,targetMoviePosition);
    }

    @OnClick(R.id.tv_tab_more_hot_point)
    public void onClick4MoreHotPoint(){
        //TODO 利用intent携带分类数据
        Intent intent = new Intent(getActivity(), MovieListActivity.class);
        intent.putExtra(Constant.ACTION_MOVIE_SELECT,Constant.DATA_MOVIE_SELECT_HOT_PIONT);
        startActivity(intent);
        Toast.makeText(getContext(),"查看更多热点",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_tab_more_music)
    public void onClick4Music(){
        Toast.makeText(getContext(),"更多最新电影",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_tab_more)
    public void onClick4More(){
        Toast.makeText(getContext(),"查看更多推荐",Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }





    private void initView(String TAG){
        //###############################以下，初始化电影封面图片############################
        if (TAG.equals(Constant.DATA_MOVIE_SELECT_HOT_PIONT)){
            Glide.with(getContext()).load(hotPiontMovies.get(0).getPicUrl()).into(mIvHotPiontMovie1);
            Glide.with(getContext()).load(hotPiontMovies.get(1).getPicUrl()).into(mIvHotPiontMovie2);
            Glide.with(getContext()).load(hotPiontMovies.get(2).getPicUrl()).into(mIvHotPiontMovie3);
            mTvTitleHotPiontMovie1.setText(hotPiontMovies.get(0).getMovieName());
            mTvTitleHotPiontMovie2.setText(hotPiontMovies.get(1).getMovieName());
            mTvTitleHotPiontMovie3.setText(hotPiontMovies.get(2).getMovieName());
        }
        if (TAG.equals(Constant.DATA_MOVIE_SELECT_LATEST)){
            Glide.with(getContext()).load(latestMovies.get(0).getPicUrl()).into(mIvLatestMovie1);
            Glide.with(getContext()).load(latestMovies.get(1).getPicUrl()).into(mIvLatestMovie2);
            Glide.with(getContext()).load(latestMovies.get(2).getPicUrl()).into(mIvLatestMovie3);
            mTvLatestMovie1.setText(latestMovies.get(0).getMovieName());
            mTvLatestMovie2.setText(latestMovies.get(1).getMovieName());
            mTvLatestMovie3.setText(latestMovies.get(2).getMovieName());
        }
        if (TAG.equals(Constant.DATA_MOVIE_SELECT_GUESS)){
            Glide.with(getContext()).load(guessMovies.get(0).getPicUrl()).into(mIvGuessMovie1);
            Glide.with(getContext()).load(guessMovies.get(1).getPicUrl()).into(mIvGuessMovie2);
            Glide.with(getContext()).load(guessMovies.get(2).getPicUrl()).into(mIvGuessMovie3);
            mTvGuessMovie1.setText(guessMovies.get(0).getMovieName());
            mTvGuessMovie2.setText(guessMovies.get(1).getMovieName());
            mTvGuessMovie3.setText(guessMovies.get(2).getMovieName());
        }
    }

    private void initData(){
        //初始化电影数据
        getMovie(getContext(),Constant.DATA_MOVIE_SELECT_HOT_PIONT);
        getMovie(getContext(),Constant.DATA_MOVIE_SELECT_LATEST);
        getMovie(getContext(),Constant.DATA_MOVIE_SELECT_GUESS);
    }
    //获取并初始化电影相关数据成员
    private void getMovie(Context context,String TAG) {
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        if (TAG.equals(Constant.DATA_MOVIE_SELECT_HOT_PIONT)) {
            bmobQuery.addWhereEqualTo("selectType", Constant.DATA_MOVIE_SELECT_HOT_PIONT);
        }else if (TAG.equals(Constant.DATA_MOVIE_SELECT_LATEST)){
            bmobQuery.groupby(new String[]{"updatedAt"});
        }else if (TAG.equals(Constant.DATA_MOVIE_SELECT_GUESS)){
            bmobQuery.groupby(new String[]{"objectId"});
        }

        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    if (TAG.equals(Constant.DATA_MOVIE_SELECT_HOT_PIONT)) {
                        hotPiontMovies = list;
                        initView(Constant.DATA_MOVIE_SELECT_HOT_PIONT);
                    }
                    if (TAG.equals(Constant.DATA_MOVIE_SELECT_LATEST)){
                        latestMovies = list;
                        initView(Constant.DATA_MOVIE_SELECT_LATEST);
                    }
                    if (TAG.equals(Constant.DATA_MOVIE_SELECT_GUESS)){
                        guessMovies = list;
                        initView(Constant.DATA_MOVIE_SELECT_GUESS);
                    }

                    Toast.makeText(context, "电影列表获取成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, TAG+"列表获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startPlayerActivity(List<Movie> targetMovieList,int position){
        String movieObjectId =targetMovieList.get(position).getObjectId();
        String movieName = targetMovieList.get(position).getMovieName();
        String moviePicUrl = targetMovieList.get(position).getPicUrl();
        String[] movieActors = targetMovieList.get(position).getActors();
        String moviePublishDate = targetMovieList.get(position).getPublishedDate();
        String movieIntro = targetMovieList.get(position).getIntroduction();
        Log.e("hotpiontFragment",movieName);
        Log.e("hotpiontFragment",moviePicUrl);
        Log.e("hotpiontFragment",movieActors[0]);
        Log.e("hotpiontFragment",moviePublishDate);
        Log.e("hotpiontFragment",movieIntro);
        Intent targetIntent = new Intent(getActivity(),VideoPlayerActivity.class);
        targetIntent.putExtra(Constant.DATA_MOVIE_OBJECT_ID,movieObjectId);
        targetIntent.putExtra(Constant.DATA_MOVIE_NAME,movieName);
        targetIntent.putExtra(Constant.DATA_MOVIE_URL,Constant.MOVIE_URL_TEST);//暂时固定死视频的URL TODO 在数据表中加入视频url连接
        targetIntent.putExtra(Constant.DATA_USER_PIC_URL,moviePicUrl);
        targetIntent.putExtra(Constant.DATA_MOVIE_ACTORS,movieActors);
        targetIntent.putExtra(Constant.DATA_MOVIE_PUBLISH_DATE,moviePublishDate);
        targetIntent.putExtra(Constant.DATA_MOVIE_INTRO,movieIntro);
        startActivity(targetIntent);
    }






}
