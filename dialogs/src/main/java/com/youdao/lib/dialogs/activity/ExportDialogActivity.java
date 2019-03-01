package com.youdao.lib.dialogs.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

/**
 *  ExportDialogActivity：题目导出弹窗
 *      ·提供QQ、微信、邮箱等三个平台的导出
 *      ·弹窗布局中 ， 即将被导出的文件的名字可定制 ， 格式是否可定制根据后续实现方案而定
 *
 */

public class ExportDialogActivity extends BaseDialogActivity {

    private String mOutputFileType;
    private String mOutputFileName;

    TextView mTvFileType;
    TextView mTvFileName;

    CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重设入场动画
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        setContentView(R.layout.dialog_export_2);
        findView();
        initSettings();

    }

    // 设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.MATCH_PARENT;
        //lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        lp.gravity = Gravity.CENTER;
        lp.x = 0;
        lp.y = 0;
        //lp.width = 100;
        //lp.height = 100;
        getWindowManager().updateViewLayout(view, lp);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        //回收hashmap中的监听器对象
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_EXPORT_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onPlatformChoosed(View view){
        //根据id确认题目导出平台，并根据tag进行回调
        int id = view.getId();
        if (id == R.id.btn_output_dialog_qq) {
            mCustomDialogManager.performExportDialogClick(CustomDialogManager.TAG_EXPORT_DIALOG_QQ);
        }else if (id == R.id.btn_output_dialog_email){
            mCustomDialogManager.performExportDialogClick(CustomDialogManager.TAG_EXPORT_DIALOG_EMAIL);
        }else if (id == R.id.btn_output_dialog_wechat){
            mCustomDialogManager.performExportDialogClick(CustomDialogManager.TAG_EXPORT_DIALOG_WECHAT);
        }
        finish();
    }

    public void onCanceled(View view){
        mCustomDialogManager.performExportDialogClick(CustomDialogManager.TAG_EXPORT_DIALOG_CANCEL);
        finish();
    }

    private void findView(){
        mTvFileName = (TextView)findViewById(R.id.tv_output_dialog_file_name);
        mTvFileType =(TextView)findViewById(R.id.tv_output_dialog_file_type);
    }

    private void initSettings(){
        //设置导出的类型名
        mTvFileName.setText(mCustomDialogManager.getOutputFileName());
        //文件类型 ， 由UI提供图片
        //mTvFileType.setText(getIntent().getStringExtra("OutputFileType"));
    }

}
