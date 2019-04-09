package com.zhuodp.graduationproject.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cn.bmob.v3.BmobUser;

/**
 *  zhuodp 2019/3/23
 *  代表用户的实体类
 *  用于用户注册登陆等操作 ，存储相关信息
 *
 */
public class User extends BmobUser {

    private String userPicUrl = "";
    private String userSignature = "个性签名未设置";
    private List<String> favorList = new ArrayList<String>(); //用户收藏的电影列表
    private LinkedList<HistoryItem> history = new LinkedList<HistoryItem>();
    public List<String> getFavorList() {
        return favorList;
    }

    public void setFavorList(String[] favorList) {
        this.favorList = Arrays.asList(favorList);
    }

    public void addFavor(String objectId){
        favorList.add(objectId);
    }

    public void removeFavor(String objectId){
        favorList.remove(favorList.get(favorList.indexOf(objectId)));
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public LinkedList<HistoryItem> getHistory() {
        return history;
    }


    public void addHistory(String movieId,String timeStamp){
        if (history.size()>20){
            history.poll();
            history.offer(new HistoryItem(movieId,timeStamp));
        }else{
            history.offer(new HistoryItem(movieId,timeStamp));
        }
    }

}