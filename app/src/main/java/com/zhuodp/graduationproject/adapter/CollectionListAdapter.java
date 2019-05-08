package com.zhuodp.graduationproject.adapter;

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
import com.zhuodp.graduationproject.entity.Movie;

import java.util.List;

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;

    public CollectionListAdapter(Context context,List<Movie> movieList){
        mContext = context;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_collection_list,null);
        return new CollectionListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.movieName.setText(mMovieList.get(position).getMovieName());
        Glide.with(mContext).load(mMovieList.get(position).getPicUrl()).placeholder(R.drawable.bg_pic_placeholder).into(myViewHolder.moviePic);
    }

    @Override
    public int getItemCount() {
        if (mMovieList!=null){
            return mMovieList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public RoundAngleImageView moviePic;
        public TextView movieName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePic = itemView.findViewById(R.id.iv_item_pic_collection_list);
            movieName = itemView.findViewById(R.id.tv_item_title_collection_list);
        }

        public RoundAngleImageView getMoviePic() {
            return moviePic;
        }

        public TextView getMovieName() {
            return movieName;
        }
    }

}
