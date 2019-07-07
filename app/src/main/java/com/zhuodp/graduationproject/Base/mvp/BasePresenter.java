package com.zhuodp.graduationproject.Base.mvp;
import android.content.Context;


/**
 * @author yutong
 * @date 2018/7/19
 */
public class BasePresenter<V extends IView> extends PresenterImp<V> {

    //protected final ILog mILog = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public BasePresenter(Context context) {
        super(context);
    }
}
