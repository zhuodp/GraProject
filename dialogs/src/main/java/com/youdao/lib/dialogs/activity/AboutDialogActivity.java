package com.youdao.lib.dialogs.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.youdao.lib.dialogs.R;
import com.youdao.lib.dialogs.base.BaseDialogActivity;
import com.youdao.lib.dialogs.manager.CustomDialogManager;

public class AboutDialogActivity extends BaseDialogActivity {

    TextView mVersion;
    CustomDialogManager mCustomDialogManager = CustomDialogManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重设入场动画
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        setContentView(R.layout.dialog_about);
        mVersion = findViewById(R.id.tv_version_dialog_about);
        mVersion.setText(getVerName(getBaseContext()));
    }

    // 设置弹窗出现的位置，即DecorView在PhoneWindow里的位置
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= (int) getResources().getDimension(R.dimen.app_448dp);
        //lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        lp.gravity = Gravity.BOTTOM;
        //lp.width = 100;
        //lp.height = 100;
        getWindowManager().updateViewLayout(view, lp);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
        //回收hashmap中的监听器对象
        mCustomDialogManager.recycleListener(mCustomDialogManager.TYPE_EXPORT_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /* 获取版本号名称*/
    private String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


}
