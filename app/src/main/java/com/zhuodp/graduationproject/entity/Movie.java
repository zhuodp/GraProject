package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
/**
 *  表示电影的实体类
 *  作为一个单位存储在Bmob上
 */
public class Movie extends BmobObject {


    private String movieName; //电影名
    private String[] actors; //演员
    private String selectType; //列表筛选标签
    private String introduction; //简介
    private String picUrl; //封面url
    private String publishedDate; //上映日期
    private String country;  //国家
    private String type;  //电影所属类型




    public String getSelectType() {
        return selectType;
    }

    public Movie(){}

    public Movie(String movieName, String[] actors, String publishedDate, String introduction, String picUrl, String selectType,String country,String type){
        this.movieName = movieName;
        this.actors = actors;
        this.introduction = introduction;
        this.publishedDate = publishedDate;
        this.picUrl = picUrl;
        this.selectType = selectType;
        this.country = country;
        this.type = type;
    }

    public Movie(Movie movie){
        this.movieName = movie.movieName;
        this.actors = movie.actors;
        this.introduction = movie.introduction;
        this.publishedDate = movie.publishedDate;
        this.picUrl = movie.picUrl;
        this.selectType = movie.selectType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
