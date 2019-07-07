package com.zhuodp.graduationproject.Base.mvp;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface IPresenter<V extends IView> extends ILifeCycle{

    void instantiate(IPresenterManager manager);

    void setIView(V view);

    void initPresenter();

    void initViewFinished();
}
