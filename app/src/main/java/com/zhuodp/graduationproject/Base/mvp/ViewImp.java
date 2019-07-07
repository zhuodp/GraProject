package com.zhuodp.graduationproject.Base.mvp;

import android.content.Context;
import android.view.ViewGroup;

import io.reactivex.annotations.Nullable;

public abstract class ViewImp<P extends IPresenter>implements IView<P> {

    protected Context mContext;

    protected P mPresenter;

    private ViewGroup mViewGroup;

    public ViewImp(Context context) {mContext = context; }

    @Override
    public void addComponents(IPresenterManager iPresenterManager) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setIPresenter(P iPresenter) {
        mPresenter = iPresenter;
    }

    @Override
    public void setLayoutViewGroup(ViewGroup viewGroup) {
        mViewGroup = viewGroup;
    }

    @Override
    @Nullable
    public ViewGroup getLayoutViewGroup() {
        return mViewGroup;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mViewGroup!=null){
            ((ViewGroup)mViewGroup.getParent()).removeAllViews();
        }
    }
}
