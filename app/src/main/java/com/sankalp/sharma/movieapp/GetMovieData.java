package com.sankalp.sharma.movieapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sankalp Sharma on 6/18/2016.
 */
public class GetMovieData extends GetRawData {

    private String LOG_TAG = GetMovieData.class.getSimpleName();
    private List<Movie> mMovies;
    private Uri mDestinationUri;

    private final String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    private final String API_TAG = "api_key";
    private final String API_KEY = "85a365eb517fe366c337edb94c0b3f40";

    public GetMovieData() {
        super(null);
        createAndUpdateUri();
        mMovies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void execute(){
        super.setRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI is: " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());

    }

    public boolean createAndUpdateUri(){
        mDestinationUri = Uri.parse(API_BASE_URL).buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_TAG, API_KEY)
                .build();

        return mDestinationUri != null;
    }

    public void processResult(){

        if (getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error downloading raw file.");
            return;
        }

        final String RESULTS = "results";
        final String ID = "id";
        final String TITLE = "original_title";
        final String RELEASE_DATE = "release_date";
        final String OVERVIEW = "overview";
        final String POSTER_PATH = "poster_path";
        final String VOTE_AVERAGE = "vote_average";


        try{
            JSONObject jsonObject = new JSONObject(getData());
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < jsonArray.length() ; i++) {

                JSONObject jsonMovie = jsonArray.getJSONObject(i);

                String title = jsonMovie.getString(TITLE);
                String releaseDate = jsonMovie.getString(RELEASE_DATE);
                String overview = jsonMovie.getString(OVERVIEW);
                String posterPath = "http://image.tmdb.org/t/p/w185/" + jsonMovie.getString(POSTER_PATH);

                long id = jsonMovie.getLong(ID);
                double voteAverage = jsonMovie.getDouble(VOTE_AVERAGE);
                String videoAPI = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=85a365eb517fe366c337edb94c0b3f40";

                Movie movie = new Movie(id, title, releaseDate, overview, posterPath, voteAverage, videoAPI);
                this.mMovies.add(movie);

            }

            for (Movie movie: mMovies) {
                Log.v(LOG_TAG, movie.toString());
            }

        }catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Error processing JSON data.");
        }
    }

    public class DownloadJsonData extends DownloadRawData{

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = { mDestinationUri.toString() };
            return super.doInBackground(par);
        }
    }

}
