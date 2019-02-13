package com.zhuodp.commondialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MyDialog extends BaseDialog {

    private OnCallResult mOnCallResult;

    private Button mCancelBtn;

    private Button mShareBtn;

    private Context mContext;

    private int mDialogLayoutId;

    private int[] mListenedItems;//监听的控件id

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //提前设置Dialog的一些阳试
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        //dialogWindow.setWindowAnimations();
        setContentView(mDialogLayoutId);

        WindowManager windowManager = ((Activity)mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        getWindow().setAttributes(layoutParams);
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
        //遍历注册id添加点击事件
        for (int id: mListenedItems){
            findViewById(id).setOnClickListener(this);
        }
    }


    public MyDialog(Context context) {
        super(context);
        initData();
        initListener();
    }

    //设置监听
    public void setCallBackListener(OnCallResult onCallResult){
        mOnCallResult = onCallResult;
    }

    @Override
    public void initView() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //setContentView();
        //设置dialog弹出、隐藏的效果
        //find view (mCancelBtn mShareBtn)；
    }

    //该方法用于操作数据
    public void initData(){

    }

    //初始化监听事件
    public void initListener(){
        mCancelBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
    }

    //监听实现
    @Override
    public void onDialogClick(View v) {
        switch (v.getId()){
            default: break;

        }
    }


}
