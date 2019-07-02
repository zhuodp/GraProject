package com.zhuodp.graduationproject.activity;
/**
 *  用户Activity
 *  在点击了立即注册之后，会调用setContentView切换布局进入注册页
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.entity.User;
import com.zhuodp.graduationproject.global.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 *  登陆界面的Activiy
 *  包含了注册和登录等与Bmob有关的逻辑
 */
public class LoginActivity extends AppBaseActivity {

    CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();
    EditText mAccountEditText;
    EditText mPasswordEditText;
    EditText mConfirmPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重设入场动画
        overridePendingTransition(com.youdao.lib.dialogs.R.anim.fade_in, com.youdao.lib.dialogs.R.anim.fade_out);
        setContentView(R.layout.activity_login_1);
        findview();
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(com.youdao.lib.dialogs.R.anim.fade_in, com.youdao.lib.dialogs.R.anim.fade_out);
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
        login(getBaseContext(), mAccountEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    public void onClick4EnterSignUpPage(View view){
        //TODO 将页面转化为注册页
        setContentView(R.layout.activity_login_2);
        findview();
        Toast.makeText(getBaseContext(),"跳转到了注册页面",Toast.LENGTH_SHORT).show();
    }

    public void onClick4SignUp(View view){
        if (mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())){
            //执行注册（网络请求）
            signUp(getBaseContext(),mAccountEditText.getText().toString(),mPasswordEditText.getText().toString());
            Log.e("AskForLoginActvitiy",mAccountEditText.getText().toString());
            Log.e("AskForLoginActvitiy",mPasswordEditText.getText().toString());
            Log.e("AskForLoginActvitiy",mConfirmPasswordEditText.getText().toString());
        }else{
            Toast.makeText(getBaseContext(),"两次输入密码不相同，请再次确认",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick4Back(View view){
        setContentView(R.layout.activity_login_1);
        findview();
    }

    private void findview(){
        mAccountEditText = (EditText)findViewById(com.youdao.lib.dialogs.R.id.et_user_account);
        mPasswordEditText =(EditText)findViewById(com.youdao.lib.dialogs.R.id.et_user_password);
        mConfirmPasswordEditText = (EditText)findViewById(com.youdao.lib.dialogs.R.id.et_user_password_confirm);
    }

    /**
     * 账号密码登录（参数定制版，用于即使在界面上更新UI）
     */
    private void login(Context context, String account, String password) {
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername(account);
        //此处替换为你的密码
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class); //利用此对象来获取头像等；
                    Toast.makeText(context,"登陆成功",Toast.LENGTH_SHORT).show();
                    //TODO 在这里把用户头像url返回给MainActivity，由MainActivity进行网络请求更新图片Glide. 与抽屉相关的逻辑都放在Activity中（暂时）
                    Intent userInfo = new Intent();//返回给MainActivity
                    userInfo.putExtra(Constant.DATA_USER_PIC_URL,user.getUserPicUrl());
                    userInfo.putExtra(Constant.DATA_USER_NAME,user.getUsername());
                    userInfo.putExtra(Constant.DATA_USER_SIGNATURE,user.getUserSignature());
                    setResult(Constant.RESULT_CODE_FOR_LOGIN_ACTIVITY_USER_INFO,userInfo);
                    finish();//推出登陆Activity，返回到MainActivity
                } else {
                    Toast.makeText(context,"登陆失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * 账号密码注册
     */
    private void signUp(Context context,String account, String password){
        final User user = new User();
        user.setUsername(account);
        user.setPassword(password);
        user.setUserPicUrl(Constant.MOVIE_PIC_URL_TEST);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(context,"注册成功，请进行登陆",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context,"注册失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
