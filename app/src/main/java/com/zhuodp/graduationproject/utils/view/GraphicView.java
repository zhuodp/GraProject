package com.zhuodp.graduationproject.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuodp.graduationproject.R;

/**
 *  自定义View ：样式为 “上图下文”
 *
 */
public class GraphicView extends RelativeLayout {

    private ImageView mImageView;
    private TextView mTextView;
    public boolean bClicked = false;
    private int mTextColor;
    private int mBgColor;
    private int mSelectedTextColor;
    private int mSelectedDrawableColor;
    private int mSelectedDrawable;
    private int mDrawableId;

    public GraphicView(Context context) {
        super(context);
        init(context, null);
    }

    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @SuppressLint("NewApi")
    public GraphicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GraphicView);
        mDrawableId = a.getResourceId(R.styleable.GraphicView_topDrawable, 0);
        String text = (String) a.getText(R.styleable.GraphicView_bottomText);
        bClicked = a.getBoolean(R.styleable.GraphicView_isClicked, bClicked);
        float textSize = a.getDimension(R.styleable.GraphicView_textSize, 10);
        mTextColor = a.getColor(R.styleable.GraphicView_textColor, getResources().getColor(R.color.color_AAB0B6));
        mBgColor = a.getColor(R.styleable.GraphicView_bgColor, getResources().getColor(R.color.color_FFFFFF));
        mSelectedTextColor = a.getColor(R.styleable.GraphicView_selectedTextColor, getResources().getColor(R.color.color_AAB0B6));
        mSelectedDrawableColor = a.getResourceId(R.styleable.GraphicView_selectedDrawableColor, 0);
        mSelectedDrawable = a.getResourceId(R.styleable.GraphicView_selectedDrawable, 0);
        a.recycle();

        setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.W1, null));
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_graphic, this, true);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.layout_graphic_layout);
        relativeLayout.setBackgroundColor(mBgColor);
        mImageView = (ImageView) inflate.findViewById(R.id.layout_graphic_iv);
        mTextView = (TextView) inflate.findViewById(R.id.layout_graphic_tv);
        if (mDrawableId != 0) {
            mImageView.setBackgroundResource(mDrawableId);
            mImageView.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.GONE);
        }
        mTextView.setText(text);
        mTextView.setTextSize(px2dip(textSize));
        mTextView.setTextColor(mTextColor);

        if (bClicked) {
            setSelectedState();
        }
    }

    private void setSelectedState() {
        if (mSelectedDrawable != 0) {
            mImageView.setBackgroundResource(mSelectedDrawable);
        } else if (mSelectedDrawableColor != 0) {
            mImageView.setBackgroundResource(mDrawableId);
            ViewCompat.setBackgroundTintList(mImageView, getResources().getColorStateList(mSelectedDrawableColor));
        }

        if (mSelectedTextColor != mTextColor) {
            mTextView.setTextColor(mSelectedTextColor);
        }
    }

    private void setDefaultState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mImageView.setBackground(null);
        }
        ViewCompat.setBackgroundTintList(mImageView, null);
        mImageView.setBackgroundResource(mDrawableId);
        mTextView.setTextColor(mTextColor);

    }

    public void setClick(boolean isClick) {
        if (isClick != bClicked) {
            if (isClick) {
                setSelectedState();
            } else {
                setDefaultState();
            }
            bClicked = isClick;
        }
    }


    public boolean isbClicked() {
        return bClicked;
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int px2dip(float pxValue) {
        float m = getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    private int sp2px(float sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics()) + 0.5f);
    }
}