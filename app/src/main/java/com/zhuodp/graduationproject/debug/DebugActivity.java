package com.zhuodp.graduationproject.debug;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class DebugActivity extends AppBaseActivity {

    private OrientationUtils orientationUtils;
    private boolean isPause =false;
    private boolean isPlay = false;

    @BindView(R.id.video_dubug_activity)
    StandardGSYVideoPlayer mVideoPlayer;

    @BindView(R.id.btn_debug_dialog)
    Button mDialogTestBtn;

    @OnClick(R.id.btn_debug_dialog)
    public void onTestButtonClick(){
        //以下用于测试Dialog库是否可用
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDialogType(CustomDialogManager.TYPE_ALTERE_DIALOG);
        customDialogManager.setAlertDialogPosText("确认");
        customDialogManager.setAlertDialogNegText("取消");
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_NEGATIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了取消按钮",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_POSITIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了确认",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.showDialog(getBaseContext());

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        initVideo();
        mVideoPlayer.startPlayLogic();

    }

    private void initVideo(){
        orientationUtils = new OrientationUtils(this,mVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        gsyVideoOptionBuilder.setIsTouchWiget(false)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                //.setUrl()
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
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

        mVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar。第二个true是否需要隐藏statusbar
                mVideoPlayer.startWindowFullscreen(DebugActivity.this,true,true);
            }
        });

        //.setThumbImageView();
    }


    //以下，debugActivity的生命周期，与video进行同步————————————————————
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
