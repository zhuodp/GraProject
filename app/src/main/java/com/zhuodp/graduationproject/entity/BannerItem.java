package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;

public class BannerItem extends BmobObject {

    private String movieName; //电影名
    private String[] actors; //演员
    private String selectType; //列表筛选标签
    private String introduction; //简介
    private String picUrl; //封面url
    private String publishedDate; //上映日期
    private String country;  //国家
    private String type;  //电影所属类型
    private String title; //Banner的标题




    public String getSelectType() {
        return selectType;
    }

    public BannerItem(){}

    public BannerItem(String movieName, String[] actors, String publishedDate, String introduction, String picUrl, String selectType,String country,String type,String title){
        this.movieName = movieName;
        this.actors = actors;
        this.introduction = introduction;
        this.publishedDate = publishedDate;
        this.picUrl = picUrl;
        this.selectType = selectType;
        this.country = country;
        this.type = type;
        this.title = title;
    }

    public BannerItem(BannerItem bannerItem){
        this.movieName = bannerItem.movieName;
        this.actors = bannerItem.actors;
        this.introduction = bannerItem.introduction;
        this.publishedDate = bannerItem.publishedDate;
        this.picUrl = bannerItem.picUrl;
        this.selectType = bannerItem.selectType;
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

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTitle() {
        return title;
    }

}
