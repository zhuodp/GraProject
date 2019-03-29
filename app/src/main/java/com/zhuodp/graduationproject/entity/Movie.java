package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
/**
 *  表示电影的实体类
 *  作为一个单位存储在Bmob上
 */
public class Movie extends BmobObject {


    private String movieName;
    private String[] actors;
    private String selectType;
    private String introduction;
    private String picUrl;
    private String publishedDate;

    public String getSelectType() {
        return selectType;
    }

    public Movie(){}

    public Movie(String movieName, String[] actors, String publishedDate, String introduction, String picUrl, String selectType){
        this.movieName = movieName;
        this.actors = actors;
        this.introduction = introduction;
        this.publishedDate = publishedDate;
        this.picUrl = picUrl;
        this.selectType = selectType;
    }

    public Movie(Movie movie){
        this.movieName = movie.movieName;
        this.actors = movie.actors;
        this.introduction = movie.introduction;
        this.publishedDate = movie.publishedDate;
        this.picUrl = movie.picUrl;
        this.selectType = movie.selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }


    public String getMovieName() {
        return movieName;
    }
    public String getPicUrl() {
        return picUrl;
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
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
