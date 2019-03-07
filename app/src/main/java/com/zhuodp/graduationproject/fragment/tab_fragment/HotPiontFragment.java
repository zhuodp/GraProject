package com.zhuodp.graduationproject.fragment.tab_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.transformer.ZoomOutTranformer;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.helper.GlideImageLoader;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO Banner实现
public class HotPiontFragment extends AppBaseFragment {

    private String[]  mBannerImageUris ={
            "https://b-ssl.duitang.com/uploads/item/201601/02/20160102175929_hATXs.jpeg"
            ,"http://pic14.photophoto.cn/20100227/0036036381162387_b.jpg"
            ,"http://5b0988e595225.cdn.sohucs.com/images/20180819/b742f087903d4bf7a7668f335106d145.jpeg"
            ,"http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20180728/14a1daaf28274d5a9f37d92da6e5b67a.jpeg"
    };

    private String[] mBannerImageTitle = {
            "1","2","3","4"
    };

    @BindView(R.id.banner_tab_hot_point)
    Banner mBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        //开始banner展示
        mBanner.start();

    }





}
