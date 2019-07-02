package com.zhuodp.graduationproject.entity;

import com.zhuodp.graduationproject.utils.bmob.BmobUtil;

import cn.bmob.v3.BmobObject;

/**
 *   单条用户播放记录的实体
 */
public class HistoryItem extends BmobObject{
    private String historyMovieId; //电影id
    private String historyTimeStamp;//历史纪录的时间戳

    public HistoryItem(String movieId,String timeStamp){
        this.historyMovieId = movieId;
        this.historyTimeStamp = timeStamp;
    }

    public String getHistoryMovieId() {
        return historyMovieId;
    }

    public String getHistoryTimeStamp() {
        return historyTimeStamp;
    }
}

