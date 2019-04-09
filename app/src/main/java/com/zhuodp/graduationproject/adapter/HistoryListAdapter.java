package com.zhuodp.graduationproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.util.RoundAngleImageView;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.entity.HistoryItem;
import com.zhuodp.graduationproject.entity.Movie;
import com.zhuodp.graduationproject.utils.bmob.BmobUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder> {

    private Context mContext;
    private LinkedList<HistoryItem> mHistoryList;

    public HistoryListAdapter(Context context,LinkedList<HistoryItem> historyList){
        mContext = context;
        mHistoryList = historyList;
    }

    @NonNull
    @Override
    public HistoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_user_history_list, parent, false);
        return new HistoryListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int position) {

        //查询数据
        BmobQuery<Movie> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mHistoryList.get(position).getHistoryMovieId(), new QueryListener<Movie>() {
            @Override
            public void done(Movie movie, BmobException e) {
                if (movie!=null){
                    myViewHolder.movieNameView.setText(movie.getMovieName());
                    myViewHolder.timeStampView.setText(mHistoryList.get(position).getHistoryTimeStamp());
                    Glide.with(mContext).load(movie.getPicUrl()).into(myViewHolder.moviePicView);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mHistoryList!=null){
            return mHistoryList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public RoundAngleImageView moviePicView;
        public TextView movieNameView;
        public TextView timeStampView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePicView = itemView.findViewById(R.id.iv_item_pic_history_list);
            movieNameView = itemView.findViewById(R.id.tv_item_title_history_list);
            timeStampView = itemView.findViewById(R.id.tv_item_time_history_list);

        }

        public RoundAngleImageView getMoviePic() {
            return moviePicView;
        }

        public TextView getMovieName() {
            return movieNameView;
        }

        public TextView getTimeStampView(){
            return timeStampView;
        }
    }

}