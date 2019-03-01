package com.zhuodp.graduationproject.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zhuodp.graduationproject.Base.AppBaseActivity;
import com.zhuodp.graduationproject.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DebugActivity extends AppBaseActivity {

    @BindView(R.id.btn_debug_activity)
    Button test1;

    @OnClick(R.id.btn_debug_activity)
    public void onTestButtonClick(){
        Intent intent = new Intent(DebugActivity.this,DebugActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
