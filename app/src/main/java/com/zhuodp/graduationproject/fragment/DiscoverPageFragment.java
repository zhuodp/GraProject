package com.zhuodp.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.CollectionActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoverPageFragment extends AppBaseFragment {

    @BindView(R.id.rl_collection_entry_collection_page)
    RelativeLayout mCollectionEntry;

    @OnClick(R.id.rl_collection_entry_collection_page)
    public void onEnterCollectionPage(){
        Intent intent = new Intent(getActivity(), CollectionActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_discover_page,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
