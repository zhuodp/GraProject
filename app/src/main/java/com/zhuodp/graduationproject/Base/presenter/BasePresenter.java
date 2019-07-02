package com.zhuodp.graduationproject.Base.presenter;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

public abstract class BasePresenter<T>  {
    //View接口类型的软引用
    //使用软引用，是为了防止所持的view都销毁了，但presenter一直持有，导致内存泄漏。
    protected Reference<T> mViewRef;

    public void attachView(T view){
        //建立对View的引用
        mViewRef = new SoftReference<T>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
        }
    }
}
