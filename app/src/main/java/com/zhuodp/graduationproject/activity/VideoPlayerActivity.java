package com.zhuodp.graduationproject.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;

import butterknife.BindView;

public class VideoPlayerActivity extends AppBaseActivity {

    //视频播放相关的成员
    private OrientationUtils orientationUtils;//帮助屏幕旋转
    private ImageView mMovieCutImage;//视频封面
    private String mMovieUrl= "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";//视频链接
    private String mMovieTitle = "测试视频";


    //用于标识当前视频播放状态
    private boolean isPause =false;
    private boolean isPlay = false;

    @BindView(R.id.video_player_activity)
    StandardGSYVideoPlayer mVideoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_player);
        initViews();
        initVideo();
    }

    private void initVideo(){
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this,mVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        //利用Builder初始化mVideoPlayer
        GSYVideoOptionBuilder gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        gsyVideoOptionBuilder
            .setThumbImageView(mMovieCutImage)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(mMovieUrl)
            .setCacheWithPlay(false)
            .setVideoTitle(mMovieTitle)
            .setVideoAllCallBack(new GSYSampleCallBack(){
                @Override
                public void onPrepared(String url,Object... objects){
                    super.onPrepared(url,objects);
                    //开始播放了才能旋转和全屏
                    orientationUtils.setEnable(true);
                    isPlay = true;
                }

                @Override
                public void onQuitFullscreen(String url,Object... objects){
                    super.onQuitFullscreen(url,objects);
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                    if (orientationUtils != null) {
                        orientationUtils.backToProtVideo();
                    }
                }
            })
            .setLockClickListener(new LockClickListener() {
                @Override
                public void onClick(View view, boolean lock) {
                    if (orientationUtils != null){
                        //配合下方的onConfigurationChanged
                        orientationUtils.setEnable(!lock);
                    }
                }
            })
            .build(mVideoPlayer);
        //设置全屏按钮的监听
        mVideoPlayer.getFullscreenButton().setOnClickListener(v -> {
            //直接横屏
            orientationUtils.resolveByClick();
            //第一个true是否需要隐藏actionbar。第二个true是否需要隐藏statusbar
            mVideoPlayer.startWindowFullscreen(VideoPlayerActivity.this,false,true);
        });
        //设置返回按钮的监听
        mVideoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
        //以下添加mViewPlayer的其他设置
    }

    private void initViews(){
        mMovieCutImage = new ImageView(this);
        //用Glide载入播放器的初始展示图片
        Glide.with(this).load(R.drawable.test_image).into(mMovieCutImage);
    }



    //****************************************以下，debugActivity的生命周期，与video进行同步*********************************************
    @Override
    public void onBackPressed(){
        if (orientationUtils != null){
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)){
            return ;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        mVideoPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume(){
        mVideoPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay){
            mVideoPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null){
            orientationUtils.releaseListener();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause){
            mVideoPlayer.onConfigurationChanged(this,newConfig,orientationUtils,true,true);
        }
    }

}
