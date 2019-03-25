package com.youdao.lib.dialogs.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

public class AskForLoginActivity extends BaseDialogActivity {

    CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();
    EditText mAccountEditText;
    EditText mPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重设入场动画
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        setContentView(R.layout.dialog_ask_for_login);
        findview();
    }

    // 设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        //lp.gravity = Gravity.CENTER;
        //lp.x = 0;
        //lp.y = 0;
        //lp.width = 100;
        //lp.height = 100;
        getWindowManager().updateViewLayout(view, lp);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        //回收hashmap中的监听器对象
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_EXPORT_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onCanceled(View view){
        mCustomDialogManager.performExportDialogClick(CustomDialogManager.TAG_EXPORT_DIALOG_CANCEL);
        finish();
    }

    public void onClick4Login(View view){
        mCustomDialogManager.performAskForLoginDialogListener(CustomDialogManager.TAG_ASK_FOR_LOGIN_DIALOG_LOGIN,
                mAccountEditText.getText().toString(),
                mPasswordEditText.getText().toString());

    }

    public void onClick4SignUp(View view){
        mCustomDialogManager.performAskForLoginDialogListener(CustomDialogManager.TAG_ASK_FOR_LOGIN_DIALOG_SIGN_UP,null,null);
    }

    private void findview(){
        mAccountEditText = (EditText)findViewById(R.id.et_user_account);
        mPasswordEditText =(EditText)findViewById(R.id.et_user_password);
    }


}
