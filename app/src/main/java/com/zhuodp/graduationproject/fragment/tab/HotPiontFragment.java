package com.zhuodp.graduationproject.fragment.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;


import butterknife.BindView;
import butterknife.OnClick;

//TODO Banner实现
public class HotPiontFragment extends AppBaseFragment {


    @BindView(R.id.tv_tab_more_hot_point)
    TextView mClickAreaForMoreHotPoint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.tv_tab_more_hot_point)
    public void onClick4MoreHotPoint(){
        Toast.makeText(getContext(),"查看更多热点",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_tab_more_music)
    public void onClick4Music(){
        Toast.makeText(getContext(),"查看更多音乐",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_tab_more)
    public void onClick4More(){
        Toast.makeText(getContext(),"查看更多...",Toast.LENGTH_SHORT).show();
    }








}
