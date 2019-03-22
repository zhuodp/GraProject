package com.zhuodp.graduationproject.bmob;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.zhuodp.graduationproject.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BombUtil {

    /**
     * 账号密码注册
     */
    private void signUp(final View view) {
        final User user = new User();
        user.setUserName("" + System.currentTimeMillis());
        user.setUserPassword("" + System.currentTimeMillis());
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 账号密码登录
     */
    private void login(final View view) {
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername("username");
        //此处替换为你的密码
        user.setPassword("password");
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 账号密码登录
     */
    private void loginByAccount(final View view) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount("username", "password", new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     *  判断当前是否有用户登陆
     */
    private boolean isLogin(){
        if (BmobUser.isLogin()) {
            //User user = BmobUser.getCurrentUser(User.class);
            //Snackbar.make(ge, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            //Snackbar.make(view, "尚未登录", Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * 获取当前用户以及用户属性
     */
    private void getCurrentUserCache(){
        /*if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            Snackbar.make(, "当前用户：" + user.getUsername() + "-" + user.getAge(), Snackbar.LENGTH_LONG).show();
            String username = (String) BmobUser.getObjectByKey("username");
            Integer age = (Integer) BmobUser.getObjectByKey("age");
            Snackbar.make(view, "当前用户属性：" + username + "-" + age, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
        }*/
    }

    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private void updateUser(final View view) {
        final User user = BmobUser.getCurrentUser(User.class);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "更新用户信息成功：" , Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "更新用户信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    /**
     * 提供旧密码修改密码
     */
    private void updatePassword(final View view){
        //TODO 此处替换为你的旧密码和新密码
        BmobUser.updateCurrentUserPassword("oldPwd", "newPwd", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 用户退出登陆
     */
    private void logout(){
        BmobUser.logOut();
    }




}
