package com.zhuodp.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.entity.SettingItem;

import java.util.List;

public class SettingListAdapter extends BaseAdapter {

    private Context context;
    private List<SettingItem> list;
    public SettingListAdapter(Context context,List<SettingItem> list){
        this.context=context;
        this.list=list;
    }

    public void setItemList(List<SettingItem> mlist){
        this.list=mlist;
    }

    @Override
    public int getCount()
    {
        if(list==null)
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }
    @Override
    public Object getItem(int i) {
        if(list==null){
            return null;
        }
        return list.get(i);
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;
        if (view == null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_lv_fragment_settings_list,null);
            viewHolder=new ViewHolder();
            viewHolder.itemName =(TextView)convertView.findViewById(R.id.tv_fragment_setting_item);
            viewHolder.itemImageId =(ImageView)convertView.findViewById(R.id.iv_fragment_setting_item);
            convertView.setTag(viewHolder);
        }
        else
        {
            convertView=view;
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.itemName.setText(list.get(i).getItemName());
        viewHolder.itemImageId.setImageResource(list.get(i).getImageId());
        return convertView;
    }

    private class ViewHolder {
        public TextView itemName;
        public ImageView itemImageId;
    }
}
