package com.zhuodp.graduationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;
import com.zhuodp.graduationproject.entity.User;
import com.zhuodp.graduationproject.fragment.DiscoverPageFragment;
import com.zhuodp.graduationproject.fragment.HomePageFragment;
import com.zhuodp.graduationproject.fragment.SettingPageFragment;
import com.zhuodp.graduationproject.global.Constant;
import com.zhuodp.graduationproject.utils.view.CircleImageView;
import com.zhuodp.graduationproject.utils.view.GraphicView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppBaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = true;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = false;

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
        setStatusBar();
        //绑定控件并初始化UI
        initViews();
        //初始化菜单栏等控件
        initSettings();
        //默认显示第一个fragment
        initFragments(0);
        //检测更新APP
        BmobUtil.queryAppUpdate(MainActivity.this);
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
        if (id == R.id.nav_download) {
            Toast.makeText(getBaseContext(),"查看本地下载",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings){
            initFragments(2);
        }
        else if (id == R.id.nav_logout) {
            userLogout();
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
            case Constant.RESULT_CODE_FOR_LOGIN_ACTIVITY_USER_INFO:
                //更新抽屉中的UI
                Glide.with(getBaseContext()).load(data.getStringExtra(Constant.DATA_USER_PIC_URL)).asBitmap().into(mUserPicInDrawer);
                mUserName.setText(data.getStringExtra(Constant.DATA_USER_NAME));
                mTvUserSignature.setText(data.getStringExtra(Constant.DATA_USER_SIGNATURE));
                break;
            default:
                break;
        }
    }


    //用户头像点击事件
    public void onUserPicClick(View view){
        userLogout();
    }
    //点击编辑用户签名
    public void onEditUserSignature(View view){
        if (BmobUtil.isLogin()) {
            //弹出个性签名修改弹窗
            CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
            customDialogManager.setDialogType(CustomDialogManager.TYPE_DATA_SETTING_DIALOG);
            customDialogManager.setDialogDismissOnTouchOutside(true);
            customDialogManager.setDataSettingButtonText("确认修改");
            customDialogManager.setDataSettingHint("编辑新的个性签名");
            customDialogManager.setOnDataSettingDialogListener(CustomDialogManager.TAG_DATA_SETTING_DIALOG_CONFIRM, new CustomDialogManager.OnDataSettingDialogListener() {
                @Override
                public void onDataSettingDialogClick(String userSignature) {
                    //确认按钮
                    //TODO 更新UI + 数据库
                    BmobUtil.updateUser(getBaseContext(), userSignature, mTvUserSignature);
                }
            });
            customDialogManager.showDialog(getBaseContext());
        }else {
            Toast.makeText(getBaseContext(),"请先登陆",Toast.LENGTH_SHORT).show();
        }
    }

    //初始化菜单栏等组件
    private void initSettings(){
        //设置toolbar
        setSupportActionBar(toolbar);
        //设置drawer相关
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //注册抽屉中菜单项的监听
        navigationView.setNavigationItemSelectedListener(this);
        //重写搜索栏的回车监听事件
        mEtSearchContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    Log.e("MainActivity","onKeyEvent + actionUp");
                    onClick4SearchMovie();
                    return true;
                }else{
                    return false;
                }
            }
        });
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
        //检测登陆状态，并同步到UI
        if (BmobUtil.isLogin()){
            User currentUser = BmobUtil.getCurrentUserCache();
            Glide.with(getBaseContext()).load(currentUser.getUserPicUrl()).asBitmap().into(mUserPicInDrawer);
            mUserName.setText(currentUser.getUsername());
            //TODO 加入签名设置的逻辑
            mTvUserSignature.setText(currentUser.getUserSignature());
        }else{
            recoverUserInfo();
        }
    }

    private void userLogout(){
        if (BmobUtil.isLogin()){
            CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
            customDialogManager.setDialogType(CustomDialogManager.TYPE_ALTERE_DIALOG);
            customDialogManager.setAlertDialogTitle("用户登出");
            customDialogManager.setAlertDialogContent("点击确定按钮即可退出登陆");
            customDialogManager.setAlertDialogPosText("确定");
            customDialogManager.setAlertDialogNegText("取消");
            customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_POSITIVE, new CustomDialogManager.AlertDialogListener() {
                @Override
                public void onAlertDialogClick() {
                    //TODO 跳转到用户个人页面
                    BmobUtil.logout();
                    recoverUserInfo(); //初始化抽屉中的用户UI
                }
            });
            customDialogManager.setDialogDismissOnTouchOutside(false);
            customDialogManager.showDialog(getBaseContext());
        }else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent, Constant.RESULT_CODE_FOR_LOGIN_ACTIVITY_USER_INFO);
        }
    }

    //初始化用户相关的UI
    public void recoverUserInfo(){
        mUserName.setText("未登录");
        mTvUserSignature.setText("未设置个性签名");
        Glide.with(getBaseContext()).load(R.drawable.pic_user_pic_default).asBitmap().into(mUserPicInDrawer);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_36404A));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {
            //android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
