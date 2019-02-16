package com.zhuodp.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuodp.graduationproject.adapter.TablayoutFragmentPagerAdapter;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.fragment.tab_fragment.CollectionFragment;
import com.zhuodp.graduationproject.fragment.tab_fragment.HotPiontFragment;
import com.zhuodp.graduationproject.fragment.tab_fragment.MovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageFragment extends Fragment {

    private List<Fragment> mTabsFragments;

    private TablayoutFragmentPagerAdapter mTablayoutFragmentPagerAdapter;

    @BindView(R.id.home_page_tab_layout)
    TabLayout mHomePageTabLayout;

    @BindView(R.id.home_page_tab_view_pager)
    ViewPager mHomePageTabViewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabFragments();
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

    private void initTabFragments(){
        mTabsFragments = new ArrayList<Fragment>();
        mTabsFragments.add(new CollectionFragment());
        mTabsFragments.add(new HotPiontFragment());
        mTabsFragments.add(new MovieFragment());
    }

    private void addTabs(){
        mHomePageTabLayout.getTabAt(0).setText("热门");
        mHomePageTabLayout.getTabAt(1).setText("剧集");
        mHomePageTabLayout.getTabAt(2).setText("电影");
    }



}
