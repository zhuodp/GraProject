package com.zhuodp.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.adapter.SettingListAdapter;
import com.zhuodp.graduationproject.debug.DebugActivity;
import com.zhuodp.graduationproject.entry.SettingItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// TODO  1.设置页面UI优化   2.用户登陆部分的逻辑 （是否能通过bmob实现用户信息存储）

public class SettingPageFragment extends AppBaseFragment {

    private List<SettingItem> mSettingItems = new ArrayList<SettingItem>();
    private SettingListAdapter mSettingListAdapter;

    @BindView(R.id.lv_fragment_setting)
    ListView mSettingListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_settings_page,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();//初始化设置列表数据
        initSettings();
    }


    private void initData(){
        mSettingItems.add(new SettingItem("使用帮助",R.drawable.ic_menu_camera));
        mSettingItems.add(new SettingItem("关于应用",R.drawable.ic_menu_camera));
        mSettingItems.add(new SettingItem("意见反馈",R.drawable.ic_menu_camera));
        mSettingItems.add(new SettingItem("更多应用",R.drawable.ic_menu_camera));
        mSettingItems.add(new SettingItem("其他设置",R.drawable.ic_menu_camera));
        mSettingItems.add(new SettingItem("测试入口",R.drawable.ic_menu_manage));

    }

    private void initSettings(){
        mSettingListAdapter =new SettingListAdapter(getActivity(),mSettingItems);
        mSettingListView.setAdapter(mSettingListAdapter);

        mSettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==5){
                    Intent intent = new Intent(getActivity(),DebugActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
