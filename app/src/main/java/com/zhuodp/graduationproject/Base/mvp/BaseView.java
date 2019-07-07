package com.zhuodp.graduationproject.Base.mvp;

import android.content.Context;

public class BaseView<P extends IPresenter> extends ViewImp<P>{
    
    //protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public BaseView(Context context) {
        super(context);
    }
}
