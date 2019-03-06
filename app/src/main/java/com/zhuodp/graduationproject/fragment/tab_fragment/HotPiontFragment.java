package com.zhuodp.graduationproject.fragment.tab_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;

import butterknife.ButterKnife;

//TODO Banner实现
public class HotPiontFragment extends AppBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



}
