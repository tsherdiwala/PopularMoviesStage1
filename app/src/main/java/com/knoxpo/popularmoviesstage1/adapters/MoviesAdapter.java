package com.knoxpo.popularmoviesstage1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knoxpo.popularmoviesstage1.R;
import com.knoxpo.popularmoviesstage1.model.Movie;
import com.knoxpo.popularmoviesstage1.viewholders.MovieVH;

import java.util.ArrayList;

/**
 * Created by Tejas Sherdiwala on 3/6/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieVH> {

    private ArrayList<Movie> mMovies;
    private LayoutInflater mInflater;
    private Context mContext;

    public MoviesAdapter(Context context,ArrayList<Movie> movies){
        mMovies = movies;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MovieVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieVH(mContext,v);
    }

    @Override
    public void onBindViewHolder(MovieVH holder, int position) {
        holder.bindMovie(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
