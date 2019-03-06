package com.zhuodp.graduationproject.debug;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.youdao.lib.dialogs.manager.CustomDialogManager;
import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DebugActivity extends AppBaseActivity {

    @BindView(R.id.btn_debug_activity)
    Button test1;

    @OnClick(R.id.btn_debug_activity)
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

    }
}
