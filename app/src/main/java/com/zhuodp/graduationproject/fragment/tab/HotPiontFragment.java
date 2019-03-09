package com.zhuodp.graduationproject.fragment.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;


import butterknife.BindView;
import butterknife.OnClick;

//TODO Banner实现
public class HotPiontFragment extends AppBaseFragment {


    @BindView(R.id.lv_tab_more_hot_point)
    RelativeLayout mClickAreaForMoreHotPoint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.lv_tab_more_hot_point)
    public void onClick4MoreHotPoint(){
        Toast.makeText(getContext(),"查看更多热点",Toast.LENGTH_SHORT).show();
    }






}
