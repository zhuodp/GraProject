package com.zhuodp.graduationproject.Base.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public String TAG = getClass().getSimpleName() + "";

    protected T mPresenter;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityView(savedInstanceState);

        mContext = BaseActivity.this;

        //创建Presenter
        mPresenter = createPresenter();

        findViewById();

        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mPresenter){
            mPresenter.attachView((V)this);
        }
    }

    /**
     * 关于Activity的界面填充的抽象方法，需要子类必须实现
     */
    protected abstract void initActivityView(Bundle savedInstanceState);

    /**
     * 加载页面元素
     */
    protected abstract void findViewById();

    /**
     * 创建Presenter 对象
     *
     * @return
     */
    protected abstract T createPresenter();

    protected abstract void getData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }
}
