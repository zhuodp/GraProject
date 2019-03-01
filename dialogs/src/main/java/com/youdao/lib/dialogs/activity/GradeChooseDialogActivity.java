package com.youdao.lib.dialogs.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.adapter.UserInfoGridViewAdapter;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

import java.util.ArrayList;
import java.util.List;

/**
 *   GradeChooseDialogActivity ： 年级选择弹窗。
 *   该弹窗功能尚未完成，暂不开放
 */
public class GradeChooseDialogActivity extends Activity {

    private CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();

    private String[] mGrades={"高一", "高二", "高三", "初一", "初二", "初三", "四年级", "五年级", "六年级", "一年级", "二年级", "三年级"};

    private List<String> mGradeList;
    //GridView适配器
    private UserInfoGridViewAdapter mAdapterForGrades;
    //表示被选中的年级
    private int selectorPositionGrades = -1;

    private GridView mGridViewForGrades;

    private Button mBtnPositive;
    private Button mBtnNegative;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        setContentView(R.layout.dialog_grade_choose);
        findView();
        initData();
        initSettings();
    }

    public void onSubmit(View view) {
        if (selectorPositionGrades == -1) {
            Toast.makeText(getApplicationContext(), "请先选择年级", Toast.LENGTH_SHORT).show();
        } else {
            mCustomDialogManager.performGradeSettingDialogClick(mCustomDialogManager.TAG_GRADE_CHOOSE_DIALOG_POSITIVE, mGrades[selectorPositionGrades]);
            finish();
        }
    }

    public void onCancel(View view){
        mCustomDialogManager.performGradeSettingDialogClick(mCustomDialogManager.TAG_GRADE_CHOOSE_DIALOG_NEGATIVE,"已取消");
        finish();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        lp.x = 0;
        lp.y = 0;
        //lp.width = 100;
        //lp.height = 100;
        getWindowManager().updateViewLayout(view, lp);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        //回收hashmap中的监听器对象
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_GRADE_CHOOSE_DIALOG);
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void findView(){
        mGridViewForGrades = (GridView)findViewById(R.id.user_info_grid_view_grade);
        mBtnPositive = (Button)findViewById(R.id.btn_user_info_page_positive);
        mBtnNegative =(Button)findViewById(R.id.btn_user_info_page_negative);
    }

    private void initData(){
        mGradeList= new ArrayList<String>();
        for(int i =0;i<mGrades.length;i++){
            mGradeList.add(mGrades[i]);
        }
    }

    private void initSettings(){
        //去除gridview默认的selector
        mGridViewForGrades.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //为gridview设置adapter
        mAdapterForGrades = new UserInfoGridViewAdapter(this,mGradeList);
        mGridViewForGrades.setAdapter(mAdapterForGrades);
        mGridViewForGrades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapterForGrades.changeState(i);
                selectorPositionGrades = i;
                mBtnPositive.setAlpha(1);

            }
        });
    }


}
