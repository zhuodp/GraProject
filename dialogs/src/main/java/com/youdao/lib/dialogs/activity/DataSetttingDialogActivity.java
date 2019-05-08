package com.youdao.lib.dialogs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

import java.util.regex.Pattern;

/**
 *   DataSetttingDialogActivity ： 数据设置的弹窗
 *   ·适用于类似 昵称输入、邮箱输入、手机输入等等场景
 *   ·弹窗布局包括一个输入框和一个确认按钮 ，其中输入框提示文字、按钮文字 都可调用方法定制
 *   ·使用方法见DebugActivity
 */
public class DataSetttingDialogActivity extends Activity {
    //邮箱格式校验
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private EditText mEmailAddress;

    private Button mSubmitButton;

    private CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重设入场动画
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        setContentView(R.layout.dialog_data_setting);
        findView();
        initSettings();
    }

    // 设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
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

    @Override
    public void finish(){
        super.finish();
        //重写activity退出动画
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        //回收CustomDialogManager中对应的监听器对象
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_DATA_SETTING_DIALOG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        mEmailAddress = (EditText)findViewById(R.id.data_setting_dialog_edit_text);
        mSubmitButton = (Button)findViewById(R.id.data_setting_dialog_submit_button);
    }

    private void initSettings(){
        //防止键盘弹出后输入框上移(当前未生效)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //设置 （输入提示的文字）和（按钮的文字）
        mEmailAddress.setHint(mCustomDialogManager.getDataSettingHint());
        mSubmitButton.setText(mCustomDialogManager.getDataSettingButtonText());
    }

    //点击了确定
    public void onSubmit(View view){
        CustomDialogManager.getInstance().performDataSettingDialogClick(mCustomDialogManager.TAG_DATA_SETTING_DIALOG_CONFIRM,mEmailAddress.getText().toString());
        finish();
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }



}
