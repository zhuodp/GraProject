package com.zhuodp.graduationproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youdao.lib.dialogs.util.RoundAngleImageView;
import com.zhuodp.graduationproject.R;
import com.zhuodp.graduationproject.entity.Movie;

import org.w3c.dom.Text;

import java.util.List;

/**
 *   影视库列表的适配器
 *
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder>{

    private Context mContext;
    private List<Movie> mMovieList;

    public MovieListAdapter(Context context,List<Movie> movieList){
        mContext = context;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_movie_list,null);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.movieName.setText(mMovieList.get(position).getMovieName());
        Glide.with(mContext).load(mMovieList.get(position).getPicUrl()).into(myViewHolder.moviePic);
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
            moviePic = itemView.findViewById(R.id.iv_movie_pic_movie_list);
            movieName = itemView.findViewById(R.id.tv_movie_name_movie_list);
        }

        public RoundAngleImageView getMoviePic() {
            return moviePic;
        }

        public TextView getMovieName() {
            return movieName;
        }
    }
}
