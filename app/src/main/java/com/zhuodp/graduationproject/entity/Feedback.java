package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;

public class Feedback extends BmobObject {
    private String userId;
    private boolean isPraise;

    public Feedback(String userId,boolean isPraise){
        this.isPraise = isPraise;
        this.userId = userId;
    }


    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

}
