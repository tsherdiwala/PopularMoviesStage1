package com.knoxpo.popularmoviesstage1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.knoxpo.popularmoviesstage1.R;
import com.knoxpo.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by asus on 3/6/2016.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "DetailActivity.EXTRA_MOVIE";

    private Movie mMovie;

    private TextView mReleaseDateTV, mVotesTV, mOverviewTV;
    private ImageView mMovieIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        init();

        updateUI();
    }

    private void init(){
        mReleaseDateTV = (TextView)findViewById(R.id.tv_release_date);
        mVotesTV = (TextView)findViewById(R.id.tv_votes);
        mOverviewTV = (TextView)findViewById(R.id.tv_overview);

        mMovieIV = (ImageView)findViewById(R.id.iv_movie);
    }


    private void updateUI(){

        mReleaseDateTV.setText(
                getString(R.string.release_date,mMovie.getReleaseDate())
        );
        mOverviewTV.setText(mMovie.getOverview());
        mVotesTV.setText(
                getString(R.string.rating,mMovie.getVoteAverage())
        );

        Picasso
                .with(this)
                .load(mMovie.getPosterPath())
                .into(mMovieIV);

        setTitle(mMovie.getTitle());
    }

}
