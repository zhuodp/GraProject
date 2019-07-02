package com.zhuodp.graduationproject.helper;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

/**
 *   装饰器类
 *   用于配置影视库的三列式RecyclerView的样式和布局
 */

public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int interval;

    public StaggeredDividerItemDecoration(Context context, int interval) {
        this.context = context;
        this.interval = interval;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //int position = parent.getChildAdapterPosition(view);
        StaggeredGridLayoutManager.LayoutParams params =(StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex(); //item下标
        int interval = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.interval, context.getResources().getDisplayMetrics());
        /**
         *假如item位于三列的中间列
         *假如位于最左列
        */

        if (spanIndex%3 == 0){
            outRect.left = interval;
            outRect.right =interval/2;
        }else if (spanIndex%3 == 1){
            outRect.left = interval/2;
            outRect.right =interval/2;
        }else if(spanIndex%3 == 2){
            outRect.left = interval/2;
            outRect.right =interval;
        }else{

        }
        outRect.top = interval;
    }

}
