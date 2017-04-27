package com.sankalp.sharma.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MOVIE_TRANSFER = "MOVIE_TRANSFER";
    private List<Movie> mMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetMovieData getMovieData = new GetMovieData();
        getMovieData.execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        ProcessMovies processMovies = new ProcessMovies();
        processMovies.execute();

        mRecyclerView.addOnItemTouchListener(new MovieRecyclerItemClickListener(this, mRecyclerView,
                new MovieRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(MOVIE_TRANSFER, mMovieRecyclerViewAdapter.getMovie(position));
                        startActivity(intent);                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        Toast.makeText(MainActivity.this, "Long tap", Toast.LENGTH_LONG).show();
                    }
                }));



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class ProcessMovies extends GetMovieData{

        public ProcessMovies() {
            super();
        }

        @Override
        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
                mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(MainActivity.this, getMovies());
                mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);
            }
        }
    }
}
