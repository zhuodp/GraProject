package com.zhuodp.graduationproject.utils;

/**
 * Created by 74021 on 2018/6/10.
 */

public class StringUtils {
    public static String getLastPathSegment(String content){
        if (content == null || content.length() == 0){
            return "";
        }
        String[] segments =content.split("/");
        if (segments.length >0){
            return segments[segments.length -1];
        }
        return "";
    }

}
