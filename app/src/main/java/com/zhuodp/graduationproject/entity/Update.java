package com.zhuodp.graduationproject.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Update extends BmobObject {

    String releaseNote;
    String versionCode;
    BmobFile APK;

    public BmobFile getApk() {
        return APK;
    }
    public void setApk(BmobFile apk) {
        this.APK = apk;
    }


    public String getText() {
        return releaseNote;
    }
    public void setText(String text) {
        this.releaseNote = text;
    }


    public String getVersionCode() {
        return versionCode;
    }


    public String getapkUrl(){
        return APK.getFileUrl();
    }


}
