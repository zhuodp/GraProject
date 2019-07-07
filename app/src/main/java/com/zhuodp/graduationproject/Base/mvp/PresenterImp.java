package com.zhuodp.graduationproject.Base.mvp;

import android.content.Context;

public abstract class PresenterImp<V extends IView> implements IPresenter<V>,IPresenterManagerFactory {
    protected Context mContext;

    protected V mView;

    public String mId;

    protected  IPresenterManager mManager;

    private boolean bDestroyed;

    //private ILog mLoggerFactory = LoggerFactory.getLogger("");

    public PresenterImp(Context context){
        mContext = context;
    }

    @Override
    public final void instantiate(IPresenterManager manager) {
        setPresenterManager(manager);
    }
    @Override
    public final void setIView(V iView) {
        mView = iView;
    }

    @Override
    public final IPresenterManager getPresenterManager() {
        return mManager;
    }

    @Override
    public final void setPresenterManager(IPresenterManager iPresenterManager) {
        mManager = iPresenterManager;
    }


    @Override
    public void initPresenter() {
    }

    @Override
    public void initViewFinished() {

    }

    @Override
    public void onCreate() {
        //mLoggerFactory.debug("%s,onCreate", this.getClass());
        if (bDestroyed) {
            bDestroyed = false;
        }
        if (mView != null) {
            mView.onCreate();
        }
    }

    @Override
    public void onStart() {
        //mLoggerFactory.debug("%s,onStart", this.getClass());
        if (bDestroyed) {
            bDestroyed = false;
        }
        if (mView != null) {
            mView.onStart();
        }
    }

    @Override
    public void onResume() {
        //mLoggerFactory.debug("%s,onResume", this.getClass());
        if (bDestroyed) {
            bDestroyed = false;
        }
        if (mView != null) {
            mView.onResume();
        }
    }

    @Override
    public void onPause() {
        //mLoggerFactory.debug("%s,onPause", this.getClass());
        if (mView != null) {
            mView.onPause();
        }
    }

    @Override
    public void onStop() {
        //mLoggerFactory.debug("%s,onStop", this.getClass());
        if (mView != null) {
            mView.onStop();
        }
    }

    @Override
    public void onDestroy() {
        //mLoggerFactory.debug("%s,onDestroy", this.getClass());
        bDestroyed = true;
        if (mView != null) {
            mView.onDestroy();
        }
    }

    public boolean isbDestroyed() {
        return bDestroyed;
    }

}