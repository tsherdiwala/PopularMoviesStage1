package com.knoxpo.popularmoviesstage1.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.knoxpo.popularmoviesstage1.R;
import com.knoxpo.popularmoviesstage1.adapters.MoviesAdapter;
import com.knoxpo.popularmoviesstage1.model.Movie;
import com.knoxpo.popularmoviesstage1.network.ApiFetchTask;
import com.knoxpo.popularmoviesstage1.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tejas Sherdiwala on 3/6/2016.
 */
public class MainActivity extends AppCompatActivity {

    private static final String
            SAVED_MOVIES = "MainActivity.SAVED_MOVIES",
            SP_SORT_BY = "MainActivity.SP_SORT_BY";

    private static final int
            SPAN_COUNT = 3,
            SORT_BY_MOST_POPULAR = 0,
            SORT_BY_HIGHEST_RATED = 1,
            DEFAULT_SORT_BY = SORT_BY_MOST_POPULAR;

    private RecyclerView mMoviesRV;
    private GetPopularMoviesTask mTask;
    private ArrayList<Movie> mMovies;
    private MoviesAdapter mAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int currentSortBy = preferences.getInt(SP_SORT_BY, DEFAULT_SORT_BY);
        int chosenSortBy = DEFAULT_SORT_BY;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.sort_popularity:
                chosenSortBy = SORT_BY_MOST_POPULAR;
                break;
            case R.id.sort_rating:
                chosenSortBy = SORT_BY_HIGHEST_RATED;
                break;
            default:
                return false;
        }

        if (chosenSortBy != currentSortBy) {
            preferences
                    .edit()
                    .putInt(SP_SORT_BY, chosenSortBy)
                    .apply();

            getMovies();
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mMoviesRV.setLayoutManager(
                new GridLayoutManager(this, SPAN_COUNT)
        );
        mMoviesRV.setAdapter(mAdapter);

        ArrayList<Movie> savedMovies = null;
        if (savedInstanceState != null) {
            savedMovies = savedInstanceState.getParcelableArrayList(SAVED_MOVIES);
        }

        if (savedMovies == null) {
            getMovies();
        } else {
            mMovies.addAll(savedMovies);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        if (mTask != null) {
            //stop the task to unnecessary fetch the data
            mTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_MOVIES, mMovies);
        super.onSaveInstanceState(outState);
    }

    private void init() {
        mMovies = new ArrayList<Movie>();
        mAdapter = new MoviesAdapter(this, mMovies);
        mMoviesRV = (RecyclerView) findViewById(R.id.rv_movies);
    }

    private void getMovies() {
        mMovies.clear();
        mAdapter.notifyDataSetChanged();
        mTask = new GetPopularMoviesTask();
        mTask.execute();
    }

    private class GetPopularMoviesTask extends ApiFetchTask<ArrayList<Movie>> {

        private static final String
                JSON_A_RESULTS = "results",
                PARAM_SORT_BY = "sort_by",
                SORT_BY_POPULARITY = "popularity.desc",
                SORT_BY_RATING = "vote_average.desc";

        public GetPopularMoviesTask() {
            super(Constants.MovieDb.URL_POPULAR);
            SharedPreferences preferences
                    = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            int sortBy = preferences.getInt(SP_SORT_BY, DEFAULT_SORT_BY);
            if (sortBy == SORT_BY_MOST_POPULAR) {
                addParam(PARAM_SORT_BY, SORT_BY_POPULARITY);
            } else if (sortBy == SORT_BY_HIGHEST_RATED) {
                addParam(PARAM_SORT_BY, SORT_BY_RATING);
            }
        }

        @Override
        protected ArrayList<Movie> parseResponse(String response) {
            ArrayList<Movie> movies = null;
            if (response != null) {
                try {
                    movies = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    JSONArray movieArray = object.getJSONArray(JSON_A_RESULTS);
                    for (int i = 0; i < movieArray.length(); i++) {
                        movies.add(new Movie(movieArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if(movies!=null){
                mMovies.addAll(movies);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}