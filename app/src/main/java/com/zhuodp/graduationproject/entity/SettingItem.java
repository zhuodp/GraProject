package com.zhuodp.graduationproject.entity;

/**
 *   实体类
 *   对应设置页面的某一个设置项
 */

public class SettingItem {
    private String itemName;
    private int imageId;
    private int forwardIconId;

    public SettingItem(String name ,int imageId,int forwardIconId){
        this.itemName = name;
        this.imageId = imageId;
        this.forwardIconId = forwardIconId;
    }

    public String getItemName(){
        return this.itemName;
    }

    public int getImageId(){
        return this.imageId;
    }

    public int getForwardIconId() {
        return forwardIconId;
    }
}
