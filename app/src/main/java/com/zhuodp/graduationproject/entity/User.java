package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobUser;

/**
 *  zhuodp 2019/3/23
 *  代表用户的实体类
 *  用于用户注册登陆等操作 ，存储相关信息
 *
 */
public class User extends BmobUser {

    private String userPicUrl = "";

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }
}