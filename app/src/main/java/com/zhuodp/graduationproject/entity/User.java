package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 *  zhuodp 2019/3/23
 *  代表用户的实体类
 *  用于用户注册登陆等操作 ，存储相关信息
 *
 */
public class User extends BmobUser {
    private String userName="";
    private String userPassword="";
    private String userPhoneNumber="";
    private String userEmail="";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }
}