package com.zhuodp.graduationproject.global;

/**
 *  Constant类：
 *   1.用于存放各种全局数据，例如Intent传输数据时的Key等等
 *
 */
public class Constant {

    //指示各种状态 1~100
    public static final int ACTIVITY_GONE = 1;
    public static final int FRAGMENT_GONE = 2;

    //MainActvitiy中Handler接收的消息 100~199

    //startActivityForResult时用到的requestCode
    public static final int RESULT_CODE_FOR_LOGIN_ACTIVITY_USER_INFO = 200;

    //用于MainActivity接受到的Intent中的数据项名称 200~299
    public static final String DATA_USER_PIC_URL = "DATA_USER_PIC_URL";
    public static final String DATA_USER_NAME = "DATA_USER_NAME";
    public static final String DATA_USER_SIGNATURE = "DATA_USER_SIGNATURE";

    public static final String DATA_MOVIE_OBJECT_ID = "DATA_MOVIE_OBJECT_ID";
    public static final String DATA_MOVIE_NAME = "DATA_MOVIE_NAME";
    public static final String DATA_MOVIE_PIC_URL = "DATA_MOVIE_PIC_URL";
    public static final String DATA_MOVIE_PUBLISH_DATE= "DATA_MOVIE_MOVIE_PUBLISH_DATE";
    public static final String DATA_MOVIE_INTRO= "DATA_MOVIE_INTRO";
    public static final String DATA_MOVIE_ACTORS= "DATA_MOVIE_ACTORS";
    public static final String DATA_MOVIE_URL = "DATA_MOVIE_URL";
    public static final String DATA_MOVIE_IS_FAVOR = "DATA_MOVIE_IS_FAVOR";

    //用于开启MovieListActivity时，进行影片筛选的字段
    public static final String ACTION_MOVIE_SELECT ="ACTION_MOVIE_SELECT";
    public static final String ACTION_MOVIE_SEARCH = "ACTION_MOVIE_SEARCH";

    public static final String DATA_MOVIE_SELECT_HOT_PIONT = "DATA_MOVIE_SELECT_HOT_PIONT";//热点
    public static final String DATA_MOVIE_SELECT_LATEST = "DATA_MOVIE_SELECT_LATEST";//最近更新
    public static final String DATA_MOVIE_SELECT_NONE = "DATA_MOVIE_SELECT_NONE"; //默认不属于任何标签
    public static final String DATA_MOVIE_SELECT_GUESS= "DATA_MOVIE_SELECT_GUESS";//热点
    public static final String DATA_MOVIE_SELECT_BANNER = "DATA_MOVIE_SELECT_BANNER";

    public static final String MOVIE_PIC_URL_TEST ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553631228268&di=b84e3a506622b15090d174fe6e77c98d&imgtype=0&src=http%3A%2F%2Fimg12.360buyimg.com%2Fn1%2Fg7%2FM03%2F08%2F12%2FrBEHZlB2PdsIAAAAAAA-q-MimnwAABrKQOArdYAAD7D526.jpg";
    public static final String MOVIE_URL_TEST = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";

}
