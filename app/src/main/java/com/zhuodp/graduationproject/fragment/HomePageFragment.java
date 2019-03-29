package com.zhuodp.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.adapter.TablayoutFragmentPagerAdapter;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.fragment.tab.CollectionTabFragment;
import com.zhuodp.graduationproject.fragment.tab.HotPiontTabFragment;
import com.zhuodp.graduationproject.fragment.tab.MovieTabFragment;
import com.zhuodp.graduationproject.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends AppBaseFragment {

    private List<Fragment> mTabsFragments;

    private TablayoutFragmentPagerAdapter mTablayoutFragmentPagerAdapter;

    @BindView(R.id.home_page_tab_layout)
    TabLayout mHomePageTabLayout;

    @BindView(R.id.home_page_tab_view_pager)
    ViewPager mHomePageTabViewPager;


    private String[]  mBannerImageUris ={
            "https://b-ssl.duitang.com/uploads/item/201601/02/20160102175929_hATXs.jpeg"
            ,"http://pic14.photophoto.cn/20100227/0036036381162387_b.jpg"
            ,"http://5b0988e595225.cdn.sohucs.com/images/20180819/b742f087903d4bf7a7668f335106d145.jpeg"
            ,"http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20180728/14a1daaf28274d5a9f37d92da6e5b67a.jpeg"
    };

    private String[] mBannerImageTitle = {
            "1","2","3","4"
    };

    @BindView(R.id.home_page_banner)
    Banner mBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabFragments();
        initBanner();

    }

    @Override
    public void onStart(){
        super.onStart();
        //切回界面时开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop(){
        super.onStop();
        //切出界面时停止轮播
        mBanner.stopAutoPlay();
    }

    private void initBanner(){
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(mBannerImageUris));
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(mBannerImageTitle));
        //自动轮播
        mBanner.isAutoPlay(true);
        //轮播时间
        mBanner.setDelayTime(2000);
        //设置指示器位置
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置Banner的点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });


        //开始banner展示
        mBanner.start();


    }

    private void initTabFragments(){
        mTabsFragments = new ArrayList<Fragment>();
        mTabsFragments.add(new HotPiontTabFragment());
        mTabsFragments.add(new CollectionTabFragment());
        mTabsFragments.add(new MovieTabFragment());

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
    }



}
