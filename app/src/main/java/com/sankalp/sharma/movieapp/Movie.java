package com.sankalp.sharma.movieapp;

import java.io.Serializable;

/**
 * Created by Sankalp Sharma on 6/18/2016.
 */
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    private long mId;
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private String mPosterPath;
    private double mVoteAverage;
    private String mTrailerId;

    public Movie(long id, String title, String releaseDate, String overview, String posterPath, double voteAverage, String trailerId) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
        mTrailerId = trailerId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return mId;
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

    public String getPosterPath() {
        return mPosterPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getTrailerId() {
        return mTrailerId;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mOverview='" + mOverview + '\'' +
                ", mPosterPath='" + mPosterPath + '\'' +
                ", mVoteAverage=" + mVoteAverage +
                ", mTrailerId='" + mTrailerId + '\'' +
                '}';
    }
}
