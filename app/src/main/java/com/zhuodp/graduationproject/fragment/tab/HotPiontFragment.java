package com.zhuodp.graduationproject.fragment.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.MovieListActivity;
import com.zhuodp.graduationproject.global.Constant;


import butterknife.BindView;
import butterknife.OnClick;

//TODO Banner实现
public class HotPiontFragment extends AppBaseFragment {


    @BindView(R.id.tv_tab_more_hot_point)
    TextView mClickAreaForMoreHotPoint;

    @BindView(R.id.movie1)
    ImageView movie1;

    @BindView(R.id.movie2)
    ImageView movie2;

    @BindView(R.id.movie3)
    ImageView movie3;

    @BindView(R.id.movie4)
    ImageView movie4;

    @BindView(R.id.movie5)
    ImageView movie5;

    @BindView(R.id.movie6)
    ImageView movie6;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_layout_hot_point_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadImage();
    }

    @OnClick(R.id.tv_tab_more_hot_point)
    public void onClick4MoreHotPoint(){
        //TODO 利用intent携带分类数据
        Intent intent = new Intent(getActivity(), MovieListActivity.class);
        intent.putExtra(Constant.KEY_MOVIE_SELECT,Constant.DATA_MOVIE_SELECT_HOT_PIONT);
        startActivity(intent);
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

    private void loadImage(){
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_1).into(movie1);
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_2).into(movie2);
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_3).into(movie3);
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_4).into(movie4);
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_5).into(movie5);
        Glide.with(getContext()).load(Constant.MOVIE_PIC_URL_6).into(movie6);
    }






}
