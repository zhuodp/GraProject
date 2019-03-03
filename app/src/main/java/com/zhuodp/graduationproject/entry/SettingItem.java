package com.zhuodp.graduationproject.entry;

/**
 *   实体类
 *   对应设置页面的某一个设置项
 */
public class SettingItem {
    private String itemName;
    private int imageId;

    public SettingItem(String name ,int imageId){
        this.itemName = name;
        this.imageId = imageId;
    }

    public String getItemName(){
        return this.itemName;
    }

    public int getImageId(){
        return this.imageId;
    }
}
