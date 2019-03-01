package com.youdao.lib.dialogs.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

/*
    zhuodp 2019/2/19
     AlertDialog : 类似于系统自带的AlertDialog 。
     提供更多的可定制功能
 */
public class AlertDialogActivity extends BaseDialogActivity {

    private Button mPositiveButton;
    private Button mNegativeButton;

    private TextView mTitle;
    private TextView mContent;

    private CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重写activity启动动画
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        setContentView(R.layout.dialog_alert);
        findView();
        //配置对话框的各种设定（包括标题、内容等等）
        initSettings();
    }

    // 在onAttachedToWindow()中设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
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
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        //回收CustomDialogManager中持有的监听器，防止内存泄露
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_ALTERE_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void findView(){
        mPositiveButton =(Button)findViewById(R.id.alert_dialog_positive_btn);
        mNegativeButton =(Button)findViewById(R.id.alert_dialog_negative_btn);
        mTitle = (TextView)findViewById(R.id.alert_dialog_title);
        mContent = (TextView)findViewById(R.id.alert_dialog_content);
    }

    private void initSettings(){
        //设置 点击弹窗外部，弹窗是否消失
        setFinishOnTouchOutside(mCustomDialogManager.getDialogDismissSetting());
        //设置对话框各处文字
        mNegativeButton.setText(mCustomDialogManager.getAlertDialogNegText());
        mPositiveButton.setText(mCustomDialogManager.getAlertDialogPosText());
        mTitle.setText(mCustomDialogManager.getAlertDialogTitle());
        mContent.setText(mCustomDialogManager.getAlertDialogContent());
    }

    public void onClick4PosButton(View view){
        mCustomDialogManager.performAlertDialogClick(mCustomDialogManager.TAG_ALERT_DIALOG_POSITIVE);
        finish();
    }

    public void onClick4NegButton(View view){
        mCustomDialogManager.performAlertDialogClick(mCustomDialogManager.TAG_ALERT_DIALOG_NEGATIVE);
        finish();
    }


}
