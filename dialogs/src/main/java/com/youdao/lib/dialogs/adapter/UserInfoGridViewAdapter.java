package com.youdao.lib.dialogs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.youdao.lib.dialogs.R;

import java.util.List;

/**
 *   UserInfoGridViewAdapter:  针对个“人信息页- 年级选择”的表格布局  ，实现的Adapter
 *   在GradeChooseDialogActivity中使用
 */
public class UserInfoGridViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;
    private int selectorPosition = -1;

    public UserInfoGridViewAdapter(Context context, List<String> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas !=null ? position : 0;
    }

    @Override
    public long getItemId(int position)
    {
        return mDatas != null ? position : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.item_gridview_grade_choose_dialog, parent,false);
        RelativeLayout itemLayout = (RelativeLayout)convertView.findViewById(R.id.user_info_grid_view_rl);
        TextView item = (TextView) convertView.findViewById(R.id.user_info_grid_view_item);
        item.setText(mDatas.get(position));
        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            item.setBackground(mContext.getResources().getDrawable(R.drawable.ic_blue_radius_22_bg));
            item.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
        } else {
            //其他的恢复原来的状态
            item.setBackground(mContext.getResources().getDrawable(R.drawable.ic_gray_radius_22_bg));
            item.setTextColor(mContext.getResources().getColor(R.color.color_36404A));
        }
        return convertView;
    }

    public void changeState(int pos){
        selectorPosition = pos;
        notifyDataSetChanged();
    }



}
