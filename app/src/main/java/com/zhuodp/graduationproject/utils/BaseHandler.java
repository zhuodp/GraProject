package com.zhuodp.graduationproject.utils;

import android.app.Activity;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zhuodp.graduationproject.global.Constant;

import java.lang.ref.WeakReference;
import android.os.Handler;

public abstract class BaseHandler extends Handler {


    protected WeakReference<Activity> activityWeakReference;
    protected WeakReference<Fragment> fragmentWeakReference;

    private BaseHandler() {
    }//构造私有化,让调用者必须传递一个Activity 或者 Fragment的实例

    public BaseHandler(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    public BaseHandler(Fragment fragment) {
        this.fragmentWeakReference = new WeakReference<>(fragment);
    }


    @Override
    public void handleMessage(Message msg) {
        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing()) {
            // 确认Activity是否不可用
            Log.e("BaseHandler","Activity is gone");
            handleMessage(msg, Constant.ACTIVITY_GONE);
        } else if (fragmentWeakReference == null || fragmentWeakReference.get() == null || fragmentWeakReference.get().isRemoving()) {
            //确认判断Fragment不可用
            Log.e("BaseHandler","Fragment is gone");
            handleMessage(msg, Constant.ACTIVITY_GONE);
        } else {
            handleMessage(msg, msg.what);
        }
    }


    /**
     * 抽象方法用户实现,用来处理具体的业务逻辑
     *
     * @param msg
     * @param what
     */
    public abstract void handleMessage(Message msg, int what);
}
