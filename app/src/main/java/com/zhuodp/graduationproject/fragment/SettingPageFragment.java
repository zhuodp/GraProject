package com.zhuodp.graduationproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.SettingListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingPageFragment extends AppBaseFragment {

    private List<String> datas;
    private SettingListAdapter mSettingListAdapter;
    @BindView(R.id.recycler_view_fragment_setting_page)
    RecyclerView mSettingsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_settings_page,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initComponent();
    }


    private void initData(){
        datas = new ArrayList<>();
        for (int i =0;i<10;i++){
            datas.add("item" + i);
        }
    }

    //初始化使用到的组件
    private void initComponent(){
        //设置RecyclerView为线性布局
        mSettingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingListAdapter = new SettingListAdapter(getContext(),datas);
        mSettingListAdapter.setmOnItemClickListener(new SettingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"点击了"+datas.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        //设置Adapter
        mSettingsRecyclerView.setAdapter(mSettingListAdapter);
    }

}
