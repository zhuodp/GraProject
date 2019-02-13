package com.zhuodp.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    public Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(Context context,int themeResId){
        super(context,themeResId);
        this.mContext = context;
    }

    public abstract void initView();
    public abstract void onDialogClick(View v);

    public void showToast(String text){
    }

    @Override
    public void onClick(View v){
        onDialogClick(v);
    }
}
