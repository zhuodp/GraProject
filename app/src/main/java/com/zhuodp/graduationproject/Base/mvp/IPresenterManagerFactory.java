package com.zhuodp.graduationproject.Base.mvp;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface IPresenterManagerFactory {

    IPresenterManager getPresenterManager();

    void setPresenterManager(IPresenterManager iPresenterManager);
}
