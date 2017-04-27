package com.sankalp.sharma.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sankalp Sharma on 6/18/2016.
 */
public class GetVideoData extends GetRawData {

    private String LOG_TAG = GetMovieData.class.getSimpleName();
    private String mVideoKey;
    private String mVideoUrl;

    public GetVideoData(String videoUrl) {
        super(null);
        mVideoUrl = videoUrl;
    }

    public void execute(){
        super.setRawUrl(mVideoUrl);

        DownloadJsonData downloadJsonData = new DownloadJsonData();
        downloadJsonData.execute(mVideoUrl);

    }

    public String getVideoKey() {
        return mVideoKey;
    }

    public void processResult(){

        if (getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error downloading raw file.");
            return;
        }

        final String RESULTS = "results";
        final String KEY = "key";

        try{
            JSONObject jsonObject = new JSONObject(getData());
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            JSONObject jsonResults = jsonArray.getJSONObject(0);
            mVideoKey = jsonResults.getString(KEY);
            System.out.println("i got data" + mVideoKey);


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
            String[] par = { mVideoUrl };
            return super.doInBackground(par);
        }
    }

}
