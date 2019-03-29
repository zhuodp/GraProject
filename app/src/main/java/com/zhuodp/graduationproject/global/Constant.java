package com.zhuodp.graduationproject.global;

public class Constant {

    //之时各种状态 1~100
    public static final int ACTIVITY_GONE = 1;
    public static final int FRAGMENT_GONE = 2;

    //MainActvitiy中Handler接收的消息 100~199

    //startActivityForResult时用到的requestCode
    public static final int REQ_CODE_FOR_LOGIN_ACTIVITY_USER_INFO = 200;

    //用于MainActivity接受到的Intent中的数据项名称 200~299
    public static final String DATA_USER_PIC_URL = "DATA_USER_PIC_URL";
    public static final String DATA_USER_NAME = "DATA_USER_NAME";

    //用于开启MovieListActivity时，进行影片筛选的字段
    public static final String KEY_MOVIE_SELECT ="KEY_MOVIE_SELECT";
    public static final String DATA_MOVIE_SELECT_HOT_PIONT = "DATA_MOVIE_SELECT_HOT_PIONT";//热点
    public static final String DATA_MOVIE_TYPE_LATEST = "DATA_MOVIE_TYPE_LATEST";//最近更新
    public static final String DATA_MOVIE_TYPE_NONE = "DATA_MOVIE_TYPE_NONE"; //默认不属于任何标签

    public static final String MOVIE_URL_TEST ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553631228268&di=b84e3a506622b15090d174fe6e77c98d&imgtype=0&src=http%3A%2F%2Fimg12.360buyimg.com%2Fn1%2Fg7%2FM03%2F08%2F12%2FrBEHZlB2PdsIAAAAAAA-q-MimnwAABrKQOArdYAAD7D526.jpg";

    //测试用的电影封面url
    public static final String MOVIE_PIC_URL_1 = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2619168989,2132540114&fm=26&gp=0.jpg";
    public static final String MOVIE_PIC_URL_2 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2986414690,4271707406&fm=26&gp=0.jpg";
    public static final String MOVIE_PIC_URL_3 = "https://b-ssl.duitang.com/uploads/item/201604/09/20160409111508_vY4nW.jpeg";
    public static final String MOVIE_PIC_URL_4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553635586589&di=167a7cff04743a4ba3dd911e2b57c5d6&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20140626%2F20140626123205-1641189658.jpg";
    public static final String MOVIE_PIC_URL_5 = "https://b-ssl.duitang.com/uploads/item/201612/11/20161211131834_5wu3k.thumb.700_0.jpeg";
    public static final String MOVIE_PIC_URL_6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553631228268&di=b84e3a506622b15090d174fe6e77c98d&imgtype=0&src=http%3A%2F%2Fimg12.360buyimg.com%2Fn1%2Fg7%2FM03%2F08%2F12%2FrBEHZlB2PdsIAAAAAAA-q-MimnwAABrKQOArdYAAD7D526.jpg";


}
