package com.zhuodp.graduationproject.debug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.MovieListActivity;
import com.zhuodp.graduationproject.activity.VideoPlayerActivity;
import com.zhuodp.graduationproject.entity.BannerItem;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;

public class DebugActivity extends AppBaseActivity {

    private List<BmobObject> mResultMovieList;

    @BindView(R.id.btn_debug_dialog)
    Button mDialogTestBtn;

    @OnClick(R.id.btn_debug_dialog)
    public void onTestButtonClick(){
        //以下用于测试Dialog库是否可用
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDialogType(CustomDialogManager.TYPE_ALTERE_DIALOG);
        customDialogManager.setAlertDialogPosText("确认");
        customDialogManager.setAlertDialogNegText("取消");
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_NEGATIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了取消按钮",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_POSITIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了确认",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.showDialog(getBaseContext());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        mResultMovieList = new ArrayList<>();
    }
    //进入视频详情页
    public void onEnterVideoPlayerPage(View view){
        Intent intent = new Intent(DebugActivity.this,VideoPlayerActivity.class);
        startActivity(intent);
    }
    //进入视频列表
    public void onEnterMovieList(View view){
        Intent intent = new Intent(DebugActivity.this, MovieListActivity.class);
        intent.putExtra(Constant.ACTION_MOVIE_SELECT,Constant.DATA_MOVIE_SELECT_NONE);
        startActivity(intent);
    }
    //更新Bmob上的视频表
    public void onMovieListUpdate(View view){
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                    for (int i =0;i<list.size();i++){
                        Movie movie = new Movie();
                        if (i%2==0){
                            movie.setSelectType(Constant.DATA_MOVIE_SELECT_HOT_PIONT);
                            movie.setCountry("美国");
                            movie.setType("剧集");
                        }else {
                            movie.setSelectType(Constant.DATA_MOVIE_SELECT_NONE);
                            movie.setCountry("日本");
                            movie.setType("恐怖");
                        }

                        movie.setObjectId(list.get(i).getObjectId());
                        mResultMovieList.add(movie);
                    }
                    upDate();
                }
                else {
                    Toast.makeText(getApplicationContext(),"查询更新项失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //弹出个性签名设置的弹窗
    public void onSetUserSignature(View view){
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_DATA_SETTING_DIALOG);
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDataSettingButtonText("确认修改");
        customDialogManager.setOnDataSettingDialogListener(CustomDialogManager.TAG_DATA_SETTING_DIALOG_CONFIRM,new CustomDialogManager.OnDataSettingDialogListener() {
            @Override
            public void onDataSettingDialogClick(String data) {
                //确认按钮
                //TODO 更新UI
                Toast.makeText(getApplicationContext(),"个性签名："+data,Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void upDate(){
        new BmobBatch().updateBatch(mResultMovieList).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Log.e("debug", "第" + i + "个数据批量更新成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            Log.e("debug","第" + i + "个数据批量更新失败：" + ex.getMessage() + "," + ex.getErrorCode());
                        }
                    }
                } else {
                    Log.e("debug","失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void addMovie(View view){
        List<BannerItem>  list = new ArrayList<BannerItem>();


        BannerItem bannerItem1 = new BannerItem("波希米亚狂想曲",
                new String[]{"拉米·马雷克","露西·宝通","格威利姆·李","本·哈迪","约瑟夫·梅泽罗"},
                "2018",
                "《波西米亚狂想曲》是布莱恩·辛格执导的音乐传记片，由拉米·马雷克、露西·宝通、格威利姆·李、本·哈迪、约瑟夫·梅泽罗联合主演，于2018年11月2日在美国上映，2019年3月22日在中国内地上映",
                "http://puui.qpic.cn/qqvideo_ori/0/k08449ujwfi_496_280/0",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "英国",
                "传记",
                "《波希米亚狂想曲》——传奇主唱弗雷迪·莫库里传记");

        BannerItem bannerItem2 = new BannerItem("绿皮书",
                new String[]{"维果·莫特森","马赫沙拉·阿里"},
                "2018",
                "《绿皮书》是由彼得·法拉利执导，维果·莫特森、马赫沙拉·阿里主演的剧情片，于2018年9月11日在多伦多国际电影节首映；2019年3月1日在中国内地上映。该片改编自真人真事，讲述了意裔美国人保镖托尼，他被聘用为世界上优秀的爵士钢琴家唐开车。钢琴家将从纽约开始举办巡回演奏，俩人之间一段跨越种族、阶级的友谊的故事",
                "http://wx1.sinaimg.cn/large/70850d11ly1g11bqhmewmj20ms0cuqdb.jpg",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "美国",
                "剧情",
                "《绿皮书》——跨越种族与阶级的友谊");

        BannerItem bannerItem3 = new BannerItem("风中有朵雨做的云",
                new String[]{"井柏然","马思纯", "宋佳" ,"秦昊","陈妍希"},
                "2019",
                "沿海小城，年轻警官杨家栋（井柏然 饰）初来乍到，恰遇城建委主任唐奕杰（张颂文 饰）坠楼身亡。杨家栋遂展开调查，发现唐奕杰坠楼案与和他过从甚密的紫金企业 负责人姜紫成（秦昊 饰），还有几年前紫金企业合伙人阿云（陈妍希 饰）失踪案都有着密切的关系。杨家栋调查中惨遭革职、追杀，一路逃往香港，途中与死者女儿小诺（马思纯 饰）意外邂逅，并在小诺的协助下继续追查，浑然不觉自己正落入一个纯情陷阱……",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554755221866&di=a7812b602124afdfd0fb7954b44ba451&imgtype=0&src=http%3A%2F%2Fwx3.sinaimg.cn%2Flarge%2Fb6940db7gy1g0rqq2pj6hj20rs0fqjsu.jpg",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "中国",
                "剧情",
                "《风中有朵雨做的云》——用电影记录时代的失语者");

        list.add(bannerItem1);
        list.add(bannerItem2);
        list.add(bannerItem3);

        for (int i= 0;i<list.size();i++){
            list.get(i).save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.e("debug","添加成功");
                    }else {
                        Log.e("debug","添加失败");
                    }
                }
            });
        }


       /* Movie movie1 = new Movie("波希米亚狂想曲",
                new String[]{"拉米·马雷克","露西·宝通","格威利姆·李","本·哈迪","约瑟夫·梅泽罗"},
                "2018",
                "《波西米亚狂想曲》是布莱恩·辛格执导的音乐传记片，由拉米·马雷克、露西·宝通、格威利姆·李、本·哈迪、约瑟夫·梅泽罗联合主演，于2018年11月2日在美国上映，2019年3月22日在中国内地上映",
                "http://puui.qpic.cn/qqvideo_ori/0/k08449ujwfi_496_280/0",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "英国",
                "传记");

        Movie movie2= new Movie("绿皮书",
                new String[]{"维果·莫特森","马赫沙拉·阿里"},
                "2018",
                "《绿皮书》是由彼得·法拉利执导，维果·莫特森、马赫沙拉·阿里主演的剧情片，于2018年9月11日在多伦多国际电影节首映；2019年3月1日在中国内地上映。该片改编自真人真事，讲述了意裔美国人保镖托尼，他被聘用为世界上优秀的爵士钢琴家唐开车。钢琴家将从纽约开始举办巡回演奏，俩人之间一段跨越种族、阶级的友谊的故事",
                "http://wx1.sinaimg.cn/large/70850d11ly1g11bqhmewmj20ms0cuqdb.jpg",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "美国",
                "剧情");

        Movie movie3 = new Movie("风中有朵雨做的云",
                new String[]{"井柏然","马思纯", "宋佳" ,"秦昊","陈妍希"},
                "2019",
                "沿海小城，年轻警官杨家栋（井柏然 饰）初来乍到，恰遇城建委主任唐奕杰（张颂文 饰）坠楼身亡。杨家栋遂展开调查，发现唐奕杰坠楼案与和他过从甚密的紫金企业 负责人姜紫成（秦昊 饰），还有几年前紫金企业合伙人阿云（陈妍希 饰）失踪案都有着密切的关系。杨家栋调查中惨遭革职、追杀，一路逃往香港，途中与死者女儿小诺（马思纯 饰）意外邂逅，并在小诺的协助下继续追查，浑然不觉自己正落入一个纯情陷阱……",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554755221866&di=a7812b602124afdfd0fb7954b44ba451&imgtype=0&src=http%3A%2F%2Fwx3.sinaimg.cn%2Flarge%2Fb6940db7gy1g0rqq2pj6hj20rs0fqjsu.jpg",
                Constant.DATA_MOVIE_SELECT_BANNER,
                "中国",
                "剧情");

        list.add(movie1);
        list.add(movie2);
        list.add(movie3);
*/





/*
        Movie movie2 = new Movie("海上钢琴师",
                new String[]{"蒂姆·罗斯","比尔·努恩","梅兰尼·蒂埃里"},
                "1998",
                "《海上钢琴师》是意大利著名导演朱塞佩·托纳托雷在1998年推出的一部文艺巨作，虽然过去了二十年，但仍然显示出了其跨越时代的生命力，这部电影充满了浪漫拉丁色彩，没有激烈精彩的枪战打斗的场面，也没有俊男靓女，只讲述了一段平凡人的传奇，却意外成功地打造了美国式的平民英雄1900，获得了众多口碑与赞誉",
                "http://image.uc.cn/s/wemedia/s/upload/2018/f08ad8e2c33339ea119bc61e0d014a85x547x800x57.jpeg",
                Constant.DATA_MOVIE_SELECT_LATEST,
                "意大利",
                "剧情");

        Movie movie3 = new Movie("情书",
                new String[]{"中山美穗","柏原崇","丰川悦司"},
                "1995",
                "该片改编自同名小说《情书》，讲述了一封原本出于哀思而寄往天国的情书，却大出意料收到同名同姓的回信，并且逐渐挖掘出一段深埋多年却始终沉静的纯真单恋的爱情故事",
                "http://www.sxdaily.com.cn/NMediaFile/2017/0213/SXRB201702131113000326021044169.jpg",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "日本",
                "爱情");

        Movie movie4 = new Movie("普罗旺斯的夏天",
                new String[]{"中山美穗","柏原崇","丰川悦司"},
                "1995",
                "《普罗旺斯的夏天》是由罗丝琳·伯胥执导，让·雷诺、安娜·加列娜、克洛伊·茹阿内、雨果·德休等人主演的法国剧情片。影片讲述居住在法国南部乡村的老人保罗和外孙外孙女一起生活的故事。影片于2014年4月2日在法国上映。",
                "https://b-ssl.duitang.com/uploads/item/201704/21/20170421112431_Evifu.thumb.700_0.jpeg",
                 Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "法国",
                "剧情");

        Movie movie5= new Movie("低俗小说",
                new String[]{"布鲁斯·威利斯","乌玛·瑟曼","约翰·特拉沃塔","阿曼达·普拉莫","蒂姆·罗斯","文·雷姆斯"},
                "1994",
                "对于昆汀迷来说，《低俗小说》是一部不可逾越的经典作品，该片更成为了昆汀拍片生涯的一部标志性影片，其开创性的格局和叙事手法，巧妙融合暴力美学、人性与社会意识，对惯常思维进行重新结构，使其获得了前所未有的赞誉与好评"
                ,"http://img2.imgtn.bdimg.com/it/u=105623955,1689473297&fm=26&gp=0.jpg",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "美国",
                "剧情");

        Movie movie6= new Movie("三傻大闹宝莱坞",
                new String[]{"阿米尔·汗","马德哈万","沙尔曼·乔什","卡琳娜·卡普"},
                "2011",
                "影片采用插叙的手法，讲述了三位主人公法罕、拉加与兰彻间的大学故事。兰彻是一个与众不同的大学生，公然顶撞院长，并质疑他的教学方法，用智慧打破学院墨守成规的传统教育观念。兰彻的特立独行引起模范学生——绰号“消音器”的查尔图的不满，他们约定十年后再一决高下，然而毕业时兰彻却选择了不告而别",
                "http://img0.imgtn.bdimg.com/it/u=2225714045,2301088247&fm=26&gp=0.jpg",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "印度",
                "喜剧");

        Movie movie7= new Movie("穿越时空的少女",
                new String[]{"仲里依纱","石田卓也","板仓光隆","垣内彩未","谷村美"},
                "2006",
                "穿越时空的少女》是由MAD HOUSE制作的动画电影，改编自筒井康隆在1967年出版的小说。 [1]  由仲里依纱、石田卓也主要配音，细田守担任导演。于2006年7月15日日本上映",
                "http://img21.mtime.cn/mg/2010/07/10/163512.78824927.jpg",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "日本",
                "动漫");


        Movie movie8= new Movie("秒速五厘米",
                new String[]{"水桥研二","近藤好美","花村怜美","尾上绫华"},
                "2007",
                "动画以一个少年为故事轴心而展开连续3个独立故事的动画短篇，时代背景是从1990年代至现代的日本，通过少年的人生展现东京以及其他地区的变迁。 [1]  第一话《樱花抄》描述贵树与明里年幼时恋爱的心情，以及他们重逢的一天，第二话《宇航员》描述以对进入高中就读的贵树怀有好感的澄田花苗的视角来展现贵树与明里分别后的生活，第三话《秒速5厘米》则刻画了贵树和明里长大后内心的种种彷徨。 [2] ",
                "http://img21.mtime.cn/mg/2010/07/10/163512.78824927.jpg",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "日本",
                "动漫");

        Movie movie9= new Movie("电锯惊魂",
                new String[]{"托宾·贝尔","肖妮·史密斯","考斯塔斯·曼迪勒","贝茜·拉塞尔","雷·沃纳尔"},
                "--",
                "http://img3.doubanio.com/view/note/l/public/p47922225.jpg",
                "《电锯惊魂》，系列性恐怖惊悚片，由马来西亚华裔导演温子仁" +
                        "（James Wan）执导。首部影片在2004年1月圣丹斯电影节首映，同年10月29日全球各地陆续上映，在北美地区带来了5518万美金的票房。由于第一集的成功，在后来的6年内制作组陆续拍摄了6部续作。影片拍摄时间之短、成本之低、票房之高创下了一个个记录，因此它被奉为经典恐怖片该系列电影由于包含过多的暴力与血腥成分，在公映后多次发生观影者被吓晕在影院的事件，建议心理承受力较差和对内脏、肢解等内容敏感者不要观看",
                Constant.DATA_MOVIE_SELECT_HOT_PIONT,
                "美国",
                "恐怖");

        list.add(movie1);
        list.add(movie2);
        list.add(movie3);
        list.add(movie4);
        list.add(movie5);
        list.add(movie6);
        list.add(movie7);
        list.add(movie8);
        list.add(movie9);
        int i;
        for (i= 0;i<list.size();i++){
            list.get(i).save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.e("debug","添加成功");
                    }else {
                        Log.e("debug","添加失败");
                    }
                }
            });

        }*/





    }

}
