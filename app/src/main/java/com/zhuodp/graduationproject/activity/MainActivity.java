package com.zhuodp.graduationproject.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.util.RoundAngleImageView;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.bmob.BmobUtil;
import com.zhuodp.graduationproject.fragment.DiscoverPageFragment;
import com.zhuodp.graduationproject.fragment.HomePageFragment;
import com.zhuodp.graduationproject.fragment.SettingPageFragment;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.BaseHandler;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomePageFragment mHomePageFragment;
    private SettingPageFragment mSettingPageFragment;
    private DiscoverPageFragment mDiscoverPageFragment;
    View mDrawerHeaderView;
    RoundAngleImageView mUserPic;
    TextView mUserName;

    //顶部栏
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //邮箱形状悬浮按钮
    @BindView(R.id.fab)
    FloatingActionButton fab;
    //左侧弹出菜单
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @OnClick(R.id.fab)
    public void onViewClicked(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @BindView(R.id.btn_fragment_home_page)
    Button mBtnFragmentHomePage;

    @BindView(R.id.btn_fragment_discover_page)
    Button mBtnFragmentDiscoverPage;

    @BindView(R.id.btn_fragment_settings_page)
    Button mBtnFragmentSettingsPage;

    @OnClick(R.id.btn_fragment_home_page)
    public void onSwitchToHomePage(){
        initFragments(0);
    }

    @OnClick(R.id.btn_fragment_discover_page)
    public void onSwitchToDisCoverPage(){
        initFragments(1);
    }

    @OnClick(R.id.btn_fragment_settings_page)
    public void onSwitchToSettingsPage(){
        initFragments(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化菜单栏等控件
        initSettings();
        //默认显示第一个fragment
        initFragments(0);
        //初始化无法用butterknige找到的view
        initViewAndListener();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //创建抽屉菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //右上方菜单监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //抽屉菜单监听
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    //用于接收从登录页等Activity返回的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            // 从LoginActivity返回的用户数据，在此更新UI
            case Constant.REQ_CODE_FOR_LOGIN_ACTIVITY_USER_INFO :
                Glide.with(getBaseContext()).load(data.getStringExtra(Constant.DATA_USER_PIC_URL)).into(mUserPic);
                mUserName.setText(data.getStringExtra(Constant.DATA_USER_NAME));
                break;
            default:
                break;
        }
    }



    //初始化菜单栏等组件
    private void initSettings(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //初始化Fragments
    private void initFragments(int btnId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (btnId){
            case 0:
                if (mHomePageFragment==null){
                    mHomePageFragment = new HomePageFragment();
                    transaction.add(R.id.content_main_for_each_fragment,mHomePageFragment);
                }else{
                    transaction.show(mHomePageFragment);
                }
                break;
            case 1:
                if (mDiscoverPageFragment==null){
                    mDiscoverPageFragment = new DiscoverPageFragment();
                    transaction.add(R.id.content_main_for_each_fragment,mDiscoverPageFragment);
                }else{
                    transaction.show(mDiscoverPageFragment);
                }
                break;
            case 2:
                if (mSettingPageFragment==null){
                    mSettingPageFragment = new SettingPageFragment();
                    transaction.add(R.id.content_main_for_each_fragment,mSettingPageFragment);
                }else{
                    transaction.show(mSettingPageFragment);
                }
                break;
            default:
                break;
        }
        //提交事务
        transaction.commit();
    }
    //隐藏fragment
    private void hideFragment(FragmentTransaction transaction){
        if (mHomePageFragment!=null){
            transaction.hide(mHomePageFragment);
        }
        if (mDiscoverPageFragment!=null){
            transaction.hide(mDiscoverPageFragment);
        }
        if(mSettingPageFragment!=null){
            transaction.hide(mSettingPageFragment);
        }
    }
    //初始化无法用butterKnife找到的View
    private void initViewAndListener(){
        mDrawerHeaderView =navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserPic = (RoundAngleImageView)mDrawerHeaderView.findViewById(R.id.iv_drawer_user_pic);
        mUserName = (TextView)mDrawerHeaderView.findViewById(R.id.tv_drawer_user_name);
        mUserPic.setOnClickListener(v -> {
            Log.d("MainActivity","点击了Drawer中的头像");
            if (BmobUtil.isLogin()){
                //TODO 跳转到用户个人页面
                Toast.makeText(getBaseContext(),"个人页面待开发，当前点击头像为登出账号",Toast.LENGTH_SHORT).show();
                BmobUtil.logout();
                mUserName.setText("未登录");
                mUserPic.setBackgroundColor(getBaseContext().getResources().getColor(R.color.B2_20_trans));
            }else{
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent, Constant.REQ_CODE_FOR_LOGIN_ACTIVITY_USER_INFO);
            }
        });
    }

    static class MyHandler extends BaseHandler{

        public MyHandler(Activity activity){
            super(activity);
        }
        @Override
        public void handleMessage(Message msg, int what) {
            switch (what){
            }
        }
    }

}
