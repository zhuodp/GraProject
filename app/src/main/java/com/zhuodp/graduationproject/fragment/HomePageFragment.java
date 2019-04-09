package com.zhuodp.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.activity.MovieListActivity;
import com.zhuodp.graduationproject.activity.UserPlayHistoryActivity;
import com.zhuodp.graduationproject.activity.VideoPlayerActivity;
import com.zhuodp.graduationproject.adapter.TablayoutFragmentPagerAdapter;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.entity.BannerItem;
import com.zhuodp.graduationproject.fragment.tab.AminTabFragment;
import com.zhuodp.graduationproject.fragment.tab.CollectionTabFragment;
import com.zhuodp.graduationproject.fragment.tab.HotPiontTabFragment;
import com.zhuodp.graduationproject.fragment.tab.MovieTabFragment;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.GlideImageLoader;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomePageFragment extends AppBaseFragment {

    private List<Fragment> mTabsFragments;

    private TablayoutFragmentPagerAdapter mTablayoutFragmentPagerAdapter;

    //Banner相关的数据
    private List<BannerItem> mBannerItems = new ArrayList<>();
    private List<String> mBannerImageUris = new ArrayList<>();
    private List<String> mBannerImageTitle = new ArrayList<>();


    @BindView(R.id.tab_layout_home_page)
    TabLayout mHomePageTabLayout;

    @BindView(R.id.home_page_tab_view_pager)
    ViewPager mHomePageTabViewPager;

    @BindView(R.id.banner_hot_point_tab)
    Banner mHotPointBanner;

    @BindView(R.id.iv_all_movies_home_page_fragment)
    ImageView mBtnAllMovies;

    @OnClick(R.id.area_enter_movie_hub)
    public void onEnterMovieHub(){
        Intent intent = new Intent(getActivity(), MovieListActivity.class);
        intent.putExtra(Constant.ACTION_MOVIE_SELECT,Constant.DATA_MOVIE_SELECT_NONE);
        startActivity(intent);
    }

    @OnClick(R.id.area_temp1)
    public void onEnterTemp1(){
        Toast.makeText(getContext(),"功能尚未开放",Toast.LENGTH_SHORT).show();
    }

    //查看用户播放纪录
    @OnClick(R.id.area_check_user_history)
    public void onCheckUserHistory(){
        if (BmobUtil.isLogin()){
            //TODO 跳转到历史纪录页面
            Intent intent = new Intent(getActivity(), UserPlayHistoryActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getContext(),"登陆后才可查看历史纪录，请先登陆",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabFragments();
    }

    @Override
    public void onStart(){
        super.onStart();
        //切回界面时开始轮播
        mHotPointBanner.startAutoPlay();
        initBanner();
    }

    @Override
    public void onStop(){
        super.onStop();
        //切出界面时停止轮播
        mHotPointBanner.stopAutoPlay();
    }


    private void initTabFragments(){
        mTabsFragments = new ArrayList<Fragment>();
        mTabsFragments.add(new HotPiontTabFragment());
        mTabsFragments.add(new CollectionTabFragment());
        mTabsFragments.add(new MovieTabFragment());
        mTabsFragments.add(new AminTabFragment());


        mHomePageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mHomePageTabViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTablayoutFragmentPagerAdapter = new TablayoutFragmentPagerAdapter(getActivity().getSupportFragmentManager(),mTabsFragments);
        mHomePageTabViewPager.setAdapter(mTablayoutFragmentPagerAdapter);
        mHomePageTabLayout.setupWithViewPager(mHomePageTabViewPager);
        //必须在setupWithViewPager之后添加标签，否则标签内容会被内部方法清除掉
        addTabs();
    }

    private void addTabs(){
        mHomePageTabLayout.getTabAt(0).setText("热门");
        mHomePageTabLayout.getTabAt(1).setText("剧集");
        mHomePageTabLayout.getTabAt(2).setText("电影");
        mHomePageTabLayout.getTabAt(3).setText("动漫");
    }

    private void initBanner(){
        BmobQuery<BannerItem> bmobQuery = new BmobQuery<BannerItem>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.addWhereEqualTo("selectType",Constant.DATA_MOVIE_SELECT_BANNER);
        bmobQuery.findObjects(new FindListener<BannerItem>() {
            @Override
            public void done(List<BannerItem> list, BmobException e) {
                if (e == null) {
                    //不改变内存地址赋值
                    mBannerItems.clear();
                    mBannerImageTitle.clear();
                    mBannerImageUris.clear();
                    mBannerItems.addAll(list);
                    //标题和图片列表赋值
                    for (int i = 0;i<mBannerItems.size();i++){
                        mBannerImageUris.add(mBannerItems.get(i).getPicUrl());
                        mBannerImageTitle.add(mBannerItems.get(i).getTitle());
                    }
                    //设置banner样式
                    mHotPointBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                    //设置图片加载器
                    mHotPointBanner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    mHotPointBanner.setImages(mBannerImageUris);
                    mHotPointBanner.setBannerAnimation(Transformer.DepthPage);
                    //设置标题集合（当banner样式有显示title时）
                    mHotPointBanner.setBannerTitles(mBannerImageTitle);
                    //自动轮播
                    mHotPointBanner.isAutoPlay(true);
                    //轮播时间
                    mHotPointBanner.setDelayTime(2000);
                    //设置指示器位置
                    mHotPointBanner.setIndicatorGravity(BannerConfig.CENTER);
                    //设置Banner的点击事件
                    mHotPointBanner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            jumpToVideoPlayer(position);
                        }
                    });
                    //开始banner展示
                    mHotPointBanner.start();
                } else {
                    Log.e("查询BannerItem项失败",e.getMessage());
                }
            }
        });
    }

    //根据banner点击位置，跳转到对应视频的播放页
    private void jumpToVideoPlayer(int position){
        String movieObjectId = mBannerItems.get(position).getObjectId();
        String movieName = mBannerItems.get(position).getMovieName();
        String moviePicUrl = mBannerItems.get(position).getPicUrl();
        String[] movieActors = mBannerItems.get(position).getActors();
        String moviePublishDate = mBannerItems.get(position).getPublishedDate();
        String movieIntro = mBannerItems.get(position).getIntroduction();
        Log.e("MovieListAc",movieName);
        Log.e("MovieListAc",moviePicUrl);
        Log.e("MovieListAc",movieActors[0]);
        Log.e("MovieListAc",moviePublishDate);
        Log.e("MovieListAc",movieIntro);


        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra(Constant.DATA_MOVIE_OBJECT_ID,movieObjectId);
        intent.putExtra(Constant.DATA_MOVIE_NAME,movieName);
        intent.putExtra(Constant.DATA_MOVIE_URL,Constant.MOVIE_URL_TEST);//暂时固定死视频的URL TODO 在数据表中加入视频url连接
        intent.putExtra(Constant.DATA_USER_PIC_URL,moviePicUrl);
        intent.putExtra(Constant.DATA_MOVIE_ACTORS,movieActors);
        intent.putExtra(Constant.DATA_MOVIE_PUBLISH_DATE,moviePublishDate);
        intent.putExtra(Constant.DATA_MOVIE_INTRO,movieIntro);
        startActivity(intent);
    }



}
