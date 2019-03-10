package com.zhuodp.graduationproject.debug;

import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class DebugActivity extends AppBaseActivity implements SurfaceHolder.Callback {



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


    @BindView(R.id.viedo_surface)
    SurfaceView mSurfaceView;

    @BindView(R.id.start)
    Button start;

    @BindView(R.id.pause)

    Button pause;
    private IjkMediaPlayer mPlayer;
    boolean isPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        initVideoSettings();

    }

    public void onClick4PlayVideo(View view){

    }

    private void initVideoSettings(){
        isPlay = false;
        pause.setEnabled(false);
        mSurfaceView.getHolder().addCallback(this);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mPlayer = new IjkMediaPlayer();
        try {
            //http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4
            mPlayer.setDataSource("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
        mPlayer.start();
    }


    @OnClick({R.id.start, R.id.pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                mPlayer.reset();
                try {
                    mPlayer.setDataSource("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4");//读取视频文件地址
                    mPlayer.prepareAsync();                             //预加载视频
                    mPlayer.setDisplay(mSurfaceView.getHolder());  //将视频画面输出到surface上
                    mPlayer.start();                                //开始播放
                    pause.setText("暂停");                        //pause此时为暂停
                    pause.setEnabled(true);                       //pause按钮此时可用
                    isPlay = true;
                }catch (IOException e){
                    Toast.makeText(getBaseContext(),"发生错误",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.pause://点击暂停时候 如果正在播放 就显示继续按钮

                if (isPlay == true) {
                    pause.setText("继续");
                    mPlayer.pause();
                    isPlay = false;
                } else {
                    mPlayer.start();
                    pause.setText("暂停");
                    isPlay = true;
                }
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
