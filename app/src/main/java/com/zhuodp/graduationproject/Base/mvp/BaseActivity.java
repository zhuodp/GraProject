/*
package com.youdao.lib.alpha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.youdao.lib.alpha.base.FragmentManagerDelegate;
import com.youdao.lib.alpha.base.PresenterManager;
import com.youdao.lib.alpha.base.inter.IFragmentManager;
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

public abstract class BaseActivity<V extends IView, P extends IPresenter> extends Activity {

    protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());
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
        mFragmentManagerDelegate = new FragmentManagerDelegate(this);
        View view = onInflateView();
        this.setContentView(view);
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
