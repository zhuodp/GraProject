package com.zhuodp.graduationproject.Base.mvp;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface IPresenterManager {

    IPresenter findPresenterById(String id);

    void addPresenter(IPresenter presenter,String id);

    void removePresenter(String id);

    void removePresenter(IPresenter presenter);

    void removeAll();

}
