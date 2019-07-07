/*
package com.youdao.lib.alpha;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youdao.lib.alpha.base.FragmentWrapper;
import com.youdao.lib.alpha.base.PresenterManager;
import com.youdao.lib.alpha.base.inter.IPresenter;
import com.youdao.lib.alpha.base.inter.IView;
import com.youdao.lib.logger.ILog;
import com.youdao.lib.logger.LoggerFactory;

import butterknife.ButterKnife;

*/
/**
 * @author yutong
 * @date 2018/7/20
 *//*

public abstract class BaseFragment<V extends IView, P extends IPresenter> extends FragmentWrapper {
    protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private P mIPresenter;
    private V mIView;
    private PresenterManager mPresenterManager;
    protected ViewGroup mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mIPresenter = createIPresent();
        mIView = createIView();
        mRootView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(mIView, mRootView);
        mIPresenter.setIView(mIView);
        mIView.setIPresenter(mIPresenter);
        mIPresenter.initPresenter();
        mIView.initView();
        mIPresenter.initViewFinished();
        mPresenterManager = new PresenterManager(mIPresenter);
        mIPresenter.instantiate(mPresenterManager);
        mIView.addComponents(mPresenterManager);
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.CREATE);
        initFragment();
        return mRootView;
    }

    @NonNull
    protected abstract V createIView();

    @NonNull
    protected abstract P createIPresent();

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initFragment(){

    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterManager.dispatcherActivityCycle(PresenterManager.PLifeCycle.DESTROY);
        mPresenterManager.removeAll();
    }

    public P getIPresenter() {
        return mIPresenter;
    }

    public V getIView() {
        return mIView;
    }
}
*/
