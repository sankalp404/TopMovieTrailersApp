package com.sankalp.sharma.movieapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Sankalp Sharma on 6/18/2016.
 */
public class MovieImageViewHolder extends RecyclerView.ViewHolder{

    protected ImageView thumbnail;

    public MovieImageViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.grid_item_image);
    }
}
