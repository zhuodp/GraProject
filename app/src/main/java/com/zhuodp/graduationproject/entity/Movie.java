package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
/**
 *  表示电影的实体类
 *  作为一个单位存储在Bmob上
 */
public class Movie extends BmobObject {
    private String name;
    private String[] actors;
    private String publishedDate;
    private String introduction;

    public Movie(String movieName,String[] actors,String publishedDate,String introduction){
        this.name = movieName;
        this.actors = actors;
        this.introduction = introduction;
        this.publishedDate = publishedDate;
    }
    public String getName() {
        return name;
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
