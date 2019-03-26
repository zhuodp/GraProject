package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
/**
 *  表示电影的实体类
 *  作为一个单位存储在Bmob上
 */
public class Movie extends BmobObject {
    private String movieName;
    private String[] actors;
    private String publishedDate;

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    private String introduction;

    public String getPicUrl() {
        return picUrl;
    }

    private String picUrl;

    public Movie(String movieName,String[] actors,String publishedDate,String introduction,String picUrl){
        this.movieName = movieName;
        this.actors = actors;
        this.introduction = introduction;
        this.publishedDate = publishedDate;
        this.picUrl = picUrl;
    }
    public String getMovieName() {
        return movieName;
    }

    public String[] getActors() {
        return actors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getIntroduction() {
        return introduction;
    }


}
