/*
package com.zhuodp.graduationproject.Base.mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;



*/
/**
 * @author yutong
 * @date 2018/7/20
 *//*

public abstract class BaseComponent<V extends IView, P extends IPresenter> extends ComponentImp<V, P> {

   //protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private IPresenterManager mIPresenterManager;

    public BaseComponent(@NonNull Context context, ViewGroup viewGroup,@NonNull IPresenterManager iPresenterManager) {
        super(context, viewGroup);
        mIPresenterManager = iPresenterManager;
        mIPresenterManager.addPresenter(mPresenter, mPresenter.getClass().getName());
    }

    @Override
    protected void onDestroy() {
        mIPresenterManager.removePresenter(mPresenter);
    }
}
*/
