package com.zhuodp.graduationproject.debug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.activity.MovieListActivity;
import com.zhuodp.graduationproject.activity.VideoPlayerActivity;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.global.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

public class DebugActivity extends AppBaseActivity {

    private List<BmobObject> mResultMovieList;

    @BindView(R.id.btn_debug_dialog)
    Button mDialogTestBtn;

    @OnClick(R.id.btn_debug_dialog)
    public void onTestButtonClick(){
        //以下用于测试Dialog库是否可用
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDialogType(CustomDialogManager.TYPE_ALTERE_DIALOG);
        customDialogManager.setAlertDialogPosText("确认");
        customDialogManager.setAlertDialogNegText("取消");
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_NEGATIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了取消按钮",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.setAlertDialogListener(CustomDialogManager.TAG_ALERT_DIALOG_POSITIVE, new CustomDialogManager.AlertDialogListener() {
            @Override
            public void onAlertDialogClick() {
                Toast.makeText(getApplicationContext(),"点击了确认",Toast.LENGTH_SHORT).show();
            }
        });
        customDialogManager.showDialog(getBaseContext());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        mResultMovieList = new ArrayList<>();
    }
    //进入视频详情页
    public void onEnterVideoPlayerPage(View view){
        Intent intent = new Intent(DebugActivity.this,VideoPlayerActivity.class);
        startActivity(intent);
    }
    //进入视频列表
    public void onEnterMovieList(View view){
        Intent intent = new Intent(DebugActivity.this, MovieListActivity.class);
        intent.putExtra(Constant.ACTION_MOVIE_SELECT,Constant.DATA_MOVIE_SELECT_NONE);
        startActivity(intent);
    }
    //更新Bmob上的视频表
    public void onMovieListUpdate(View view){
        BmobQuery<Movie> bmobQuery = new BmobQuery<Movie>();
        bmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                    for (int i =0;i<list.size();i++){
                        Movie movie = new Movie();
                        if (i%2==0){
                            movie.setSelectType(Constant.DATA_MOVIE_SELECT_HOT_PIONT);
                        }else {
                            movie.setSelectType(Constant.DATA_MOVIE_SELECT_NONE);
                        }
                        movie.setObjectId(list.get(i).getObjectId());
                        mResultMovieList.add(movie);
                    }
                    upDate();
                }
                else {
                    Toast.makeText(getApplicationContext(),"查询更新项失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //弹出个性签名设置的弹窗
    public void onSetUserSignature(View view){
        CustomDialogManager customDialogManager = CustomDialogManager.getInstance();
        customDialogManager.setDialogType(CustomDialogManager.TYPE_DATA_SETTING_DIALOG);
        customDialogManager.setDialogDismissOnTouchOutside(true);
        customDialogManager.setDataSettingButtonText("确认修改");
        customDialogManager.setOnDataSettingDialogListener(CustomDialogManager.TAG_DATA_SETTING_DIALOG_CONFIRM,new CustomDialogManager.OnDataSettingDialogListener() {
            @Override
            public void onDataSettingDialogClick(String data) {
                //确认按钮
                //TODO 更新UI
                Toast.makeText(getApplicationContext(),"个性签名："+data,Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void upDate(){
        new BmobBatch().updateBatch(mResultMovieList).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Log.e("debug", "第" + i + "个数据批量更新成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            Log.e("debug","第" + i + "个数据批量更新失败：" + ex.getMessage() + "," + ex.getErrorCode());
                        }
                    }
                } else {
                    Log.e("debug","失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
