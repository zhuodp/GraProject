/*
package com.zhuodp.graduationproject.Base.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;


import butterknife.ButterKnife;

*/
/**
 * @author yutong
 * @date 2018/7/19
 *//*

public abstract class BaseCompatActivity<V extends IView, P extends IPresenter> extends AppCompatActivity {

    //protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private P mIPresenter;
    private V mIView;
    private FragmentManagerDelegate mFragmentManagerDelegate;
    private PresenterManager mPresenterManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
        View view = onInflateView();
        this.setContentView(view);
        mFragmentManagerDelegate = new FragmentManagerDelegate(this);
        mIPresenter = createIPresent();
        mIView = createIView();
        ButterKnife.bind(mIView, view);
        mIPresenter.setIView(mIView);
        mIView.setIPresenter(mIPresenter);
        mIPresenter.initPresenter();
        mIView.initView();
        mIPresenter.initViewFinished();
        mPresenterManager = new PresenterManager(mIPresenter);
        mIPresenter.instantiate(mPresenterManager);
        mIView.addComponents(mPresenterManager);
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.CREATE);
        IMMLeaks.fixFocusedViewLeak(ActivityStack.getInstance().getContext());
    }

    public void onShowPermissions() {
    }

    public IFragmentManager getFragmentManagerDelegate() {
        return mFragmentManagerDelegate;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected final View onInflateView() {
        return LayoutInflater.from(this).inflate(getLayoutId(), null);
    }

    @NonNull
    protected abstract V createIView();

    @NonNull
    protected abstract P createIPresent();

    @Override
    protected void onStart() {
        super.onStart();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.START);
        onShowPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.DESTROY);
        mPresenterManager.removeAll();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    public P getIPresenter() {
        return mIPresenter;
    }

    public V getIView() {
        return mIView;
    }
}
*/
