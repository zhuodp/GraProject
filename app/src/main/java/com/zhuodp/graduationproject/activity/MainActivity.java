package com.zhuodp.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.bmob.BmobUtil;
import com.zhuodp.graduationproject.fragment.DiscoverPageFragment;
import com.zhuodp.graduationproject.fragment.HomePageFragment;
import com.zhuodp.graduationproject.fragment.SettingPageFragment;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.view.CircleImageView;
import com.zhuodp.graduationproject.utils.view.GraphicView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomePageFragment mHomePageFragment;
    private SettingPageFragment mSettingPageFragment;
    private DiscoverPageFragment mDiscoverPageFragment;

    View mDrawerHeaderView;//抽屉头部View
    CircleImageView mUserPicInDrawer;//抽屉中的用户头像
    TextView mUserName; //抽屉中的用户名
    Button mBtnEditSignature;//编辑个性签名按钮
    TextView mTvUserSignature;//用户个性签名展示

    //顶部栏
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //左侧弹出菜单
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.et_toolbar_search_content)
    EditText mEtSearchContent;

    @BindView(R.id.btn_toolbar_search)
    Button mBtnSearch;

    @BindView(R.id.btn_fragment_home_page)
    GraphicView mBtnFragmentHomePage;

    @BindView(R.id.btn_fragment_discover_page)
    GraphicView mBtnFragmentDiscoverPage;

    @BindView(R.id.btn_fragment_settings_page)
    GraphicView mBtnFragmentSettingsPage;


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


    @OnClick(R.id.btn_toolbar_search)
    public void onClick4SearchMovie(){
        Toast.makeText(getBaseContext(),"搜索了"+mEtSearchContent.getText().toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,MovieListActivity.class);
        intent.putExtra(Constant.ACTION_MOVIE_SEARCH,mEtSearchContent.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件并初始化
        initViews();
        //初始化菜单栏等控件
        initSettings();
        //默认显示第一个fragment
        initFragments(0);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                //更新抽屉中的UI
                Glide.with(getBaseContext()).load(data.getStringExtra(Constant.DATA_USER_PIC_URL)).asBitmap().into(mUserPicInDrawer);
                mUserName.setText(data.getStringExtra(Constant.DATA_USER_NAME));
                break;
            default:
                break;
        }
    }

    //用户头像点击事件
    public void onUserPicClick(View view){
        if (BmobUtil.isLogin()){
            Toast.makeText(getBaseContext(),"当前点击操作为退出登陆",Toast.LENGTH_SHORT).show();
            //TODO 跳转到用户个人页面
            BmobUtil.logout();
            if (mSettingPageFragment!=null){
                mSettingPageFragment.recoverUserInfo(); //设置也如果已经被加载了，就对其用户部分进行初始化，统一两处的UI
            }
            recoverUserInfo(); //初始化抽屉中的用户UI
        }else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent, Constant.REQ_CODE_FOR_LOGIN_ACTIVITY_USER_INFO);
        }
    }
    //点击编辑用户签名
    public void onEditUserSignature(View view){
        //弹出个性签名修改弹窗
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_DATA_SETTING_DIALOG);
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDataSettingButtonText("确认修改");
        customDialogManager.setDataSettingHint("编辑新的个性签名");
        customDialogManager.setOnDataSettingDialogListener(CustomDialogManager.TAG_DATA_SETTING_DIALOG_CONFIRM,new CustomDialogManager.OnDataSettingDialogListener() {
            @Override
            public void onDataSettingDialogClick(String userSignature) {
                //确认按钮
                //TODO 更新UI + 数据库
                BmobUtil.updateUser(getBaseContext(),userSignature,mTvUserSignature);
            }
        });
        customDialogManager.showDialog(getBaseContext());
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
                mBtnFragmentHomePage.setClick(true);
                mBtnFragmentDiscoverPage.setClick(false);
                mBtnFragmentSettingsPage.setClick(false);
                if (mHomePageFragment==null){
                    mHomePageFragment = new HomePageFragment();
                    transaction.add(R.id.content_main_for_each_fragment,mHomePageFragment);
                }else{
                    transaction.show(mHomePageFragment);
                }
                break;
            case 1:
                mBtnFragmentHomePage.setClick(false);
                mBtnFragmentDiscoverPage.setClick(true);
                mBtnFragmentSettingsPage.setClick(false);
                if (mDiscoverPageFragment==null){
                    mDiscoverPageFragment = new DiscoverPageFragment();
                    transaction.add(R.id.content_main_for_each_fragment,mDiscoverPageFragment);
                }else{
                    transaction.show(mDiscoverPageFragment);
                }
                break;
            case 2:
                mBtnFragmentHomePage.setClick(false);
                mBtnFragmentDiscoverPage.setClick(false);
                mBtnFragmentSettingsPage.setClick(true);
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

    private void initViews(){
        mDrawerHeaderView =navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserPicInDrawer = mDrawerHeaderView.findViewById(R.id.iv_drawer_user_pic);
        mUserName = mDrawerHeaderView.findViewById(R.id.tv_drawer_user_name);
        mBtnEditSignature = mDrawerHeaderView.findViewById(R.id.btn_drawer_edit);
        mTvUserSignature = mDrawerHeaderView.findViewById(R.id.tv_drawer_user_signature);
    }

    public void recoverUserInfo(){
        mUserName.setText("未登录");
        Glide.with(getBaseContext()).load(R.drawable.user_pic_test).asBitmap().into(mUserPicInDrawer);
    }

}
