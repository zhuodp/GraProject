package com.zhuodp.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.LoginActivity;
import com.zhuodp.graduationproject.adapter.SettingListAdapter;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;
import com.zhuodp.graduationproject.debug.DebugActivity;
import com.zhuodp.graduationproject.entity.SettingItem;
import com.zhuodp.graduationproject.entity.User;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// TODO  1.设置页面UI优化   2.用户登陆部分的逻辑 （是否能通过bmob实现用户信息存储）

public class SettingPageFragment extends AppBaseFragment {

    private List<SettingItem> mSettingItems = new ArrayList<SettingItem>();
    private SettingListAdapter mSettingListAdapter;

    @BindView(R.id.lv_fragment_setting)
    ListView mSettingListView;

    @BindView(R.id.circle_iv_user_pic)
    CircleImageView mSettingPageUserPic;

    @BindView(R.id.tv_setting_page_user_name)
    TextView mUserName;

    @BindView(R.id.tv_setting_page_user_signature)
    TextView mUserSignature;

    //抽屉中的用户头像
    CircleImageView mDrawerUserPic;
    //抽屉中的用户名
    TextView mDrawerUserName;
    //抽屉中的用户个性签名
    TextView mDrawerUseSignature;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_settings_page,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();//初始化设置列表数据
        initSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
        //如果已经登陆，则更新用户信息，否则初始化
        if (BmobUtil.isLogin()){
            User currentUser = BmobUtil.getCurrentUserCache();
            Glide.with(getContext()).load(currentUser.getUserPicUrl()).asBitmap().into(mSettingPageUserPic);
            mUserName.setText(currentUser.getUsername());
            //TODO 加入签名设置的逻辑
            mUserSignature.setText(currentUser.getUserSignature());
        }else{
            recoverUserInfo();
        }
    }

    @OnClick(R.id.circle_iv_user_pic)
    public void onUserPicClick(){
        if (!BmobUtil.isLogin()){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,Constant.RESULT_CODE_FOR_LOGIN_ACTIVITY_USER_INFO);
        }else{
            Toast.makeText(getContext(),"跳转到个人用户页面",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        mDrawerUserPic=getActivity().findViewById(R.id.iv_drawer_user_pic);
        mDrawerUserName =getActivity().findViewById(R.id.tv_drawer_user_name);
        mDrawerUseSignature =getActivity().findViewById(R.id.tv_drawer_user_signature);
    }

    private void initData(){
        mSettingItems.add(new SettingItem("使用帮助",R.drawable.ic_menu_camera,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("关于应用",R.drawable.ic_menu_camera,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("意见反馈",R.drawable.ic_menu_camera,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("更多应用",R.drawable.ic_menu_camera,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("其他设置",R.drawable.ic_menu_camera,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("测试入口",R.drawable.ic_menu_manage,R.drawable.ic_shortcut_arrow_forward));

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

    //初始化用户信息（若在抽屉中推出，）
    public void recoverUserInfo(){
        Glide.with(getContext()).load(R.drawable.user_pic_test).asBitmap().into(mSettingPageUserPic);
        Glide.with(getContext()).load(R.drawable.user_pic_test).asBitmap().into(mDrawerUserPic);
        mUserName.setText(R.string.not_login_tip_1);
        mUserSignature.setText(R.string.not_login_tip_2);
        mDrawerUserName.setText(R.string.not_login_tip_1);
        mDrawerUseSignature.setText(R.string.not_login_tip_2);
    }

}
