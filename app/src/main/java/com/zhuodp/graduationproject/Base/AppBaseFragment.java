package com.zhuodp.graduationproject.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AppBaseFragment extends Fragment {
    //ButterKnife 绑定使用
    private Unbinder unbinder;
    @Nullable

    @Override
    public void  onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
