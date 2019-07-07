package com.zhuodp.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseFragment;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.LoginActivity;
import com.zhuodp.graduationproject.adapter.SettingListAdapter;
import com.zhuodp.graduationproject.entity.Feedback;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
/**
 *  设置页对应的Fragment；
 */
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
        mSettingItems.add(new SettingItem("使用帮助",R.drawable.icon_use_help_setting_page,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("关于应用",R.drawable.icon_about_us_setting_page,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("意见反馈",R.drawable.icon_feed_back_setting_page,R.drawable.ic_shortcut_arrow_forward));
        mSettingItems.add(new SettingItem("测试入口",R.drawable.icon_test_entry_setting_page,R.drawable.ic_shortcut_arrow_forward));

    }

    private void initSettings(){
        mSettingListAdapter =new SettingListAdapter(getActivity(),mSettingItems);
        mSettingListView.setAdapter(mSettingListAdapter);
        mSettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://使用帮助
                        showUseHelpDialog();
                        break;
                    case 1://关于应用
                        showAboutDialog();
                        break;
                    case 2://意见反馈
                        if (BmobUtil.isLogin()){
                            showFeedBackDialog(BmobUtil.getCurrentUser().getObjectId());
                        }else {
                            showFeedBackDialog("未登录用户");
                        }

                        break;
                    case 3://测试入口
                        Intent intent = new Intent(getActivity(),DebugActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //初始化用户信息（若在抽屉中推出，）
    public void recoverUserInfo(){
        Glide.with(getContext()).load(R.drawable.pic_user_pic_default).asBitmap().into(mSettingPageUserPic);
        Glide.with(getContext()).load(R.drawable.pic_user_pic_default).asBitmap().into(mDrawerUserPic);
        mUserName.setText(R.string.not_login_tip_1);
        mUserSignature.setText(R.string.not_login_tip_2);
        mDrawerUserName.setText(R.string.not_login_tip_1);
        mDrawerUseSignature.setText(R.string.not_login_tip_2);
    }

    private void showFeedBackDialog(String userId){
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_FEED_BACK_DIALOG);
        customDialogManager.setDialogDismissOnTouchOutside(false);
        customDialogManager.setOnFeedBackDialogListener(CustomDialogManager.TAG_FEED_BACK_DIALOG_GOOD, new CustomDialogManager.OnFeedbackDialogListener() {
            @Override
            public void onFeedbackDialogClick() {
                Feedback feedback= new Feedback(userId,true);
                feedback.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(getContext(),"您的评价我们已经收到，感谢有你",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("SettingFragment","评价失败"+e.getMessage());
                            Toast.makeText(getContext(),"抱歉出错了，请稍后再试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        customDialogManager.setOnFeedBackDialogListener(CustomDialogManager.TAG_FEED_BACK_DIALOG_BAD, new CustomDialogManager.OnFeedbackDialogListener() {
            @Override
            public void onFeedbackDialogClick() {
                //差评
                Feedback feedback= new Feedback(userId,false);
                feedback.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(getContext(),"您的评价我们已经收到，感谢有你",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("SettingFragment","评价失败"+e.getMessage());
                            Toast.makeText(getContext(),"抱歉出错了，请稍后再试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        customDialogManager.showDialog(getContext());
    }

    private void showUseHelpDialog(){
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_USER_HELP);
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.showDialog(getContext());
    }

    private void showAboutDialog(){
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_ABOUT);
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.showDialog(getContext());
    }

}
