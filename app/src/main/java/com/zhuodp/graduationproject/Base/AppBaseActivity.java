package com.zhuodp.graduationproject.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.zhuodp.graduationproject.bmob.BmobUtil;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class AppBaseActivity extends AppCompatActivity {

    String[] actors = new String[]{"娜塔莉·波特曼","XXX","王尼玛"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Bomb
        Bmob.initialize(this,"badb1f749a06652383ab72d20a5e2eff");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        //TODO 删掉测试例子
        /*Movie movie = new Movie("哈哈",
                actors,"2018",
                "没有介绍",
                Constant.MOVIE_PIC_URL_TEST);
        movie.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });*/
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState); 将该回调设置为空实现，避免APP崩溃重启时，出现Fragment重叠的现象
    }
}
