package com.zhuodp.graduationproject.fragment.hot_tab_fragment;

import android.icu.util.Measure;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.utils.LoopViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HotPiontFragment extends Fragment {

    private int[] mBannerPics;
/*
    @BindView(R.id.home_page_loop_view_pager)
    LoopViewPager mLoopViewPager;

    @BindView(R.id.home_page_loop_view_pager_image_view)
    ImageView mImageViewBanner;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBannerPics[0] = R.drawable.ic_menu_camera;
        mBannerPics[1] = R.drawable.ic_menu_gallery;
        mBannerPics[2] = R.drawable.ic_menu_manage;

        mLoopViewPager.setAdapter(new MyAdapter() );
        mLoopViewPager.setOffscreenPageLimit(3);
        mLoopViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.9f;
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position >=0 && position <=1){
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                }else if (position > -1 && position <0){
                    page.setScaleY(1 + (1 - scale) * position);
                }else {
                    page.setScaleY(scale);
                }
            }
        });
        mLoopViewPager.autoLoop(true);
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBannerPics.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position){
            View view = View.inflate(getContext().getApplicationContext(),R.layout.home_page_loop_view_pager_item,null);
            mImageViewBanner.setImageResource(mBannerPics[position]);
            container.addView(view);
            return view ;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }*/
}
