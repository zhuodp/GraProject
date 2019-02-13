package com.zhuodp.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.effect.Effect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogBuilder extends Dialog implements DialogInterface {
    private final String mTextColor = "#FFFFFFFF";//字体颜色

    private final String mDividerColor = "#FFFFFFFF"; //线条颜色

    private final String mMsgColor = "";

    private final String mDialogColor = "";

    private static Context mCurContext;

    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;

    private FrameLayout mFrameLayoutView;

    private View mDialogView;

    private View mDivider;

    private TextView mTitle;

    private TextView mMessage;

    private ImageView mIcon;

    private Button mOkButton;

    private Button mCancelButton;

    private static  int mOrientation = 1;

    private boolean isCancelable = true;

    private static DialogBuilder mDialogBuilderInstance;

    //构造器
    public DialogBuilder(@NonNull Context context,int theme) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width =ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
    }
    //单例
    public static DialogBuilder getInstance(Context context){
        if (mDialogBuilderInstance == null || !mCurContext.equals(context)){
            synchronized (DialogBuilder.class){
                if (mDialogBuilderInstance == null || !mCurContext.equals(context)){
                    mDialogBuilderInstance = new DialogBuilder(context,R.style.Theme_AppCompat_Dialog);
                }
            }
        }
        mCurContext = context;
        return mDialogBuilderInstance;
    }

    /**
     *  初始化控件
     * @param context
     *
     */
    private void init(Context context){
        /*mDialogView = View.inflate(context, R.layout.dialog_layout, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);
        mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.customPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButton1 = (Button) mDialogView.findViewById(R.id.button1);
        mButton2 = (Button) mDialogView.findViewById(R.id.button2);*/

        setContentView(mDialogView);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mLinearLayoutView.setVisibility(View.VISIBLE);

            }
        });

    }
}
