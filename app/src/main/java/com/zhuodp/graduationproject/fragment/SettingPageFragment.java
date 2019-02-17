package com.zhuodp.graduationproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingPageFragment extends AppBaseFragment {


    private List<String> datas;
    @BindView(R.id.recycler_view_fragment_setting_page)
    RecyclerView recyclerView;


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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置Adapter
        recyclerView.setAdapter(new SettingListAdapter(getContext(),datas));
    }
    private class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.MyViewHolder>{

        //当前上下文对象
        Context context;
        //RecyclerView填充数据
        List<String> datas;

        public SettingListAdapter(Context context,List<String> datas){
            this.context = context;
            this.datas =  datas;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =View.inflate(context,R.layout.fragment_setting_page_recycler_view_item,null);
            //返回新建的MyViewHolder对象，后续待优化
            return new MyViewHolder(view);
        }

        //绑定数据
        @Override
        public void onBindViewHolder(@NonNull SettingListAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.fragment_settings_page_recycler_view_item);
            }
        }
    }
}
