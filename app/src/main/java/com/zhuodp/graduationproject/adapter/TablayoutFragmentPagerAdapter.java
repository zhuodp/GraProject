package com.zhuodp.graduationproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *  主页中Tablayout的适配器
 */
public class TablayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager; //创建FragmentManageer
    private List<Fragment> fragmentList; //创建FragmentList
    //构造函数

    public TablayoutFragmentPagerAdapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.fragmentList=list;
        this.fragmentManager=fm;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0){
        //TODO Auto-generated method stub
        return fragmentList.get(arg0);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }
}
