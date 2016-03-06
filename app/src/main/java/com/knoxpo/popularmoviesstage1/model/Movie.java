package com.knoxpo.popularmoviesstage1.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by asus on 3/6/2016.
 */
public class Movie implements Parcelable {

    private static final String
            POSTER_BASE_URL = "http://image.tmdb.org/t/p/",
            POSTER_SIZE = "w185",
            JSON_S_TITLE = "title",
            JSON_S_POSTER_PATH = "poster_path",
            JSON_D_VOTE_AVERAGE = "vote_average",
            JSON_S_RELEASE_DATE = "release_date",
            JSON_S_OVERVIEW = "overview";

    private String
            mPosterPath,
            mTitle,
            mReleaseDate,
            mOverview;

    private double mVoteAverage;

    public Movie(JSONObject object) {
        mTitle = object.optString(JSON_S_TITLE);
        String posterPath = object.optString(JSON_S_POSTER_PATH, null);
        if (posterPath != null) {
            StringBuilder builder = new StringBuilder(POSTER_BASE_URL);
            builder
                    .append(POSTER_SIZE)
                    .append(posterPath);
            mPosterPath = builder.toString();
        }

        mReleaseDate = object.optString(JSON_S_RELEASE_DATE);
        mOverview = object.optString(JSON_S_OVERVIEW);

        mVoteAverage = object.optDouble(JSON_D_VOTE_AVERAGE);
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in){
        mPosterPath=in.readString();
        mTitle=in.readString();
        mReleaseDate=in.readString();
        mOverview=in.readString();
        mVoteAverage=in.readDouble();
    }
}