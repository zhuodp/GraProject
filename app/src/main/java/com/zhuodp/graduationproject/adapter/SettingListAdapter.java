package com.zhuodp.graduationproject.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuodp.graduationproject.R;

import java.util.List;

public class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.MyViewHolder>{

    //当前上下文对象
    private Context context;
    //RecyclerView填充数据
    private List<String> datas;
    //item点击回调
    private OnItemClickListener mOnItemClickListener;


    public SettingListAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas =  datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_setting_page_recycler_view_item,parent,false);
        //返回新建的MyViewHolder对象，后续待优化
        return new MyViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull SettingListAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(datas.get(position));
        if (mOnItemClickListener!=null){
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //设置item点击回调
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        RelativeLayout itemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fragment_settings_page_recycler_view_item);
            imageView = itemView.findViewById(R.id.fragment_settings_page_recycler_view_item_img);
            imageView.setImageResource(R.drawable.bmob_update_btn_check_off_focused_holo_light);
            itemLayout =(RelativeLayout)itemView.findViewById(R.id.fragment_settings_page_recycler_view_item_layout);
        }
    }

}
