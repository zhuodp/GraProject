package com.youdao.lib.dialogs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

public class FeedBackDialog extends Activity{

    CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗口外部点击
        setFinishOnTouchOutside(mCustomDialogManager.getDialogDismissSetting());
        //重写activity启动动画
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.dialog_feedback);

    }

    // 在onAttachedToWindow()中设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.x = 0;
        lp.y = (int) getResources().getDimension(R.dimen.app_35dp);
        //lp.width = 100;
        //lp.height = 100;
        getWindowManager().updateViewLayout(view, lp);
    }

    //重写finish函数
    @Override
    public void finish(){
        super.finish();
        //设置activity退出动画
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        mCustomDialogManager.recycleListener(CustomDialogManager.TYPE_FEED_BACK_DIALOG);
    }

    public void onGoodFeedBack(View view){
        mCustomDialogManager.performFeedbackDialogClick(CustomDialogManager.TAG_FEED_BACK_DIALOG_GOOD);
        finish();
    }

    public void onBadFeedBack(View view){
        mCustomDialogManager.performFeedbackDialogClick(CustomDialogManager.TAG_FEED_BACK_DIALOG_BAD);
        finish();
    }

    public void onCancel(View view){
        mCustomDialogManager.performFeedbackDialogClick(CustomDialogManager.TAG_FEED_BACK_DIALOG_CANCEL);
        finish();
    }

}

