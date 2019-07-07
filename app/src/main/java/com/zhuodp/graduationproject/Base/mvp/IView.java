package com.zhuodp.graduationproject.Base.mvp;

import android.support.annotation.RestrictTo;
import android.view.ViewGroup;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface IView<P extends  IPresenter> extends ILifeCycle {

    void setIPresenter(P iPresenter);

    void initView();

    void setLayoutViewGroup(ViewGroup viewGroup);

    ViewGroup getLayoutViewGroup();

    /**
     * 如果涉及组件，这里进行添加相关组件信息操作
     * @param iPresenterManager iPresenterManager
     */
    void addComponents(IPresenterManager iPresenterManager);

}
