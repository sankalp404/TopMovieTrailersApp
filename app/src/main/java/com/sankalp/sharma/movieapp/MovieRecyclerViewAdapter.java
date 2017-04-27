package com.sankalp.sharma.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sankalp Sharma on 6/18/2016.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieImageViewHolder> {

    private List<Movie> mMovieList;
    private Context mContext;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movieList) {
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public MovieImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
        return new MovieImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieImageViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(mContext).load(movie.getPosterPath())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
//        holder.title.setText(photo.getTitle());

    }

    @Override
    public int getItemCount() {
        return (null != mMovieList ? mMovieList.size() : 0);
    }

    public Movie getMovie(int position){
        return (null != mMovieList ? mMovieList.get(position) : null);
    }

    public void loadNewData(List<Movie> newMovies){
        mMovieList = newMovies;
        notifyDataSetChanged();
    }
}
