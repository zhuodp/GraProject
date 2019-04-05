package com.zhuodp.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhuodp.graduationproject.R;

public class FilterAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    private String rankType;

    public FilterAdapter(String[] data,String rankType,Context context){
        super();
        this.data = data;
        this.rankType = rankType;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ViewHolder viewHolder;
        if (view== null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_gridview_sort_by_type_movie_list,null);
            viewHolder=new FilterAdapter.ViewHolder();
            viewHolder.itemText = convertView.findViewById(R.id.item_tv_sort_by_type_movie_list);
            convertView.setTag(viewHolder);
        }
        else
        {
            convertView=view;
            viewHolder=(FilterAdapter.ViewHolder)convertView.getTag();
        }

        viewHolder.itemText.setText(data[position]);
        return convertView;
    }

    private class ViewHolder{
        private TextView itemText;
    }

}
