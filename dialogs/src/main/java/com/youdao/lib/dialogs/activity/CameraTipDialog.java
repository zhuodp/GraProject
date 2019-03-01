package com.youdao.lib.dialogs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;

public class CameraTipDialog extends BaseDialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重写activity启动动画
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.dialog_cemara_tip);
    }

    // 在onAttachedToWindow()中设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.x = 0;
        lp.y = 0;
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
    }
}
