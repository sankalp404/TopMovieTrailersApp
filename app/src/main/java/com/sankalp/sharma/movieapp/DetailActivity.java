package com.sankalp.sharma.movieapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final String MOVIE_TRANSFER = "MOVIE_TRANSFER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        setupActionBar();

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(MOVIE_TRANSFER);

        TextView movieTitle = (TextView) findViewById(R.id.title);
        TextView movieOverview = (TextView) findViewById(R.id.overview);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        ImageButton trailer = (ImageButton) findViewById(R.id.trailerButton);

        final GetVideoData getVideoData = new GetVideoData(movie.getTrailerId());
        getVideoData.execute();

        try {

            movieTitle.setText(movie.getTitle());

            movieOverview.setText(movie.getOverview());

            releaseDate.setText("Release Date: " + movie.getReleaseDate());

            trailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("SANKALP SAYS: " + getVideoData.getVideoKey());
                    watchYoutubeVideo(getVideoData.getVideoKey());
                }
            });

        }catch (NullPointerException e){
            Log.e(LOG_TAG, "Null Pointer Exception: " + e);
        }catch (Exception e){

        }

        ImageView photoImage = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(movie.getPosterPath())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
