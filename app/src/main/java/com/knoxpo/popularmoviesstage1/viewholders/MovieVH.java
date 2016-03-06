package com.knoxpo.popularmoviesstage1.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.knoxpo.popularmoviesstage1.R;
import com.knoxpo.popularmoviesstage1.activities.DetailActivity;
import com.knoxpo.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Tejas Sherdiwala on 3/6/2016.
 */
public class MovieVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMovieIV;
    private Context mContext;
    private Movie mMovie;

    public MovieVH(Context context,View itemView) {
        super(itemView);
        mContext = context;
        mMovieIV = (ImageView)itemView.findViewById(R.id.iv_movie);
        itemView.setOnClickListener(this);
    }

    public void bindMovie(Movie movie){
        mMovie = movie;

        Picasso
                .with(mContext)
                .load(movie.getPosterPath())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(mMovieIV);
    }

    @Override
    public void onClick(View v) {
        if(mMovie != null){
            Intent detailIntent = new Intent(
                    mContext,
                    DetailActivity.class
            );

            detailIntent.putExtra(DetailActivity.EXTRA_MOVIE,mMovie);
            mContext.startActivity(detailIntent);
        }
    }
}
