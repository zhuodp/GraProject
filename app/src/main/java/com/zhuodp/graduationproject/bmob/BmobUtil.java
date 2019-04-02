package com.zhuodp.graduationproject.bmob;

import android.app.ActivityManager;
import android.app.Person;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.MainActivity;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.entity.User;
import com.zhuodp.graduationproject.global.Constant;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *   zhuodp 2019/3/29
 *
 *  1.根据账号密码注册signUp
 *  2.根据账号密码登陆login
 *  3.判断当前是否有用户登陆
 *  4.获取用户属性
 *  5.提供旧密码设置新密码 updatePassword
 *  6.登出 logout
 *  7.继承其他定制(只适用于本项目特定场景)方法
 */
public class BmobUtil {

    private static boolean isLoginSuccess = false;//标注登陆是否成功
    private static boolean isSignUpSuccess = false;//标注注册是否成功
    /**
     * 账号密码注册
     */
    public static void signUp(Context context,String account, String password){
        final User user = new User();
        user.setUsername(account);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    isSignUpSuccess = true;
                    Toast.makeText(context,"注册成功，请返回进行登陆",Toast.LENGTH_SHORT).show();
                } else {
                    isSignUpSuccess = false;
                    Toast.makeText(context,"注册失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 账号密码登录（参数定制版，用于即使在界面上更新UI）
     */
    public static void login(Context context, String account, String password) {
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

                } else {
                    Toast.makeText(context,"登陆失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 账号密码登录
     */
    public static void loginByAccount(final View view) {
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
    public static boolean isLogin(){
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
    public static User getCurrentUserCache() {
        if (BmobUser.isLogin()) {
            return BmobUser.getCurrentUser(User.class);
            /*Snackbar.make(, "当前用户：" + user.getUsername() + "-" + user.getAge(), Snackbar.LENGTH_LONG).show();

            String username = (String) BmobUser.getObjectByKey("username");
            Integer age = (Integer) BmobUser.getObjectByKey("age");
            Snackbar.make(view, "当前用户属性：" + username + "-" + age, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
        }*/
        }else{
           return null;
        }
    }

    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    public static void updateUser(Context context,String userSignature,TextView ui) {
        final User user = BmobUser.getCurrentUser(User.class);
        user.setUserSignature(userSignature);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ui.setText(userSignature);
                    Toast.makeText(context,"个性签名更新成功",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"个性签名更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    /**
     * 提供旧密码修改密码
     */
    public static void updatePassword(final View view){
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
    public static void logout(){
        BmobUser.logOut();
    }


    /**
     *
     * 添加一个在电影表中添加一个电影项
     */
    public static void addMovie(Context context,Movie movie){
        movie.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    movie.setObjectId(objectId);
                    Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(context,"添加失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //更新电影的是否为收藏的属性
    public static void updateMovieFavorState(Context context,String movieObjectId,boolean isFavor){
        BmobQuery<Movie> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(movieObjectId, new QueryListener<Movie>() {
            @Override
            public void done(Movie movie, BmobException e) {
                movie.setFavor(isFavor);
                movie.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }



}
