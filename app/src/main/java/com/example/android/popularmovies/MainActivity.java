package com.example.android.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.JsonUtils;

public class MainActivity extends AppCompatActivity implements PosterGridAdapter.GridItemClickListener, AsyncResult {

    private static final String SELECTED_SORT = "SELECTED_SORT";
    public static final String SELECTED_MOVIE = "SELECTED_MOVIE";
    private static final String MOVIES = "MOVIES";

    private PosterGridAdapter mPosterGridAdapter;
    private ProgressBar mProgressBar;
    private TextView mResponse;
    private RecyclerView mRecyclerView;
    private Movies mMovies = new Movies();
    private AlertDialog mFilterSelectionAlertDialog;
    private int mSelectedSort;
    private JsonUtils mJsonUtils = new JsonUtils(this);

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            mMovies = (Movies) savedInstanceState.getSerializable(MOVIES);
            mSelectedSort = savedInstanceState.getInt(SELECTED_SORT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_SORT, mSelectedSort);
        outState.putSerializable(MOVIES, mMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mainFrame = findViewById(R.id.layout_main);
        mainFrame.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        mResponse = findViewById(R.id.tv_response);
        mProgressBar = findViewById(R.id.pb_loading);
        mRecyclerView = findViewById(R.id.rv_main);

        if(savedInstanceState != null) {
            mSelectedSort = savedInstanceState.getInt(SELECTED_SORT);
            mMovies = (Movies)savedInstanceState.getSerializable(MOVIES);
            configurePosterGridView(true, true);
        }
        else {
            mSelectedSort = 0;
            getMovies(NetworkUtils.SORT_POPULAR);
            configurePosterGridView(false, false);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(SELECTED_MOVIE, mPosterGridAdapter.getItem(clickedItemIndex));
        startActivity(intent);
    }

    public void getMovies(String sortBy) {
        for(int i = 1; i < 10; i++) {
            new CollectMovieDataTask(
                    MainActivity.this,
                    sortBy, i)
                    .execute();
        }

        //TODO: Implement infinite scrolling
    }

    @Override
    public void onTaskFinish(String output) {
        if(output != null) {
            mMovies.addAll(mJsonUtils.parseMoviesFromJson(output));
            configurePosterGridView(true, true);
        }
        else {
            // No response from server, notify user
            configurePosterGridView(true, false);
        }
    }

    public void configurePosterGridView(boolean loadFinished, boolean hasResults) {
        int orientation = getResources().getConfiguration().orientation;
        int spanCount = orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));

        if(!loadFinished && !hasResults) {
            mProgressBar.setVisibility(View.VISIBLE);
            mResponse.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        else if(loadFinished && !hasResults) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mResponse.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mResponse.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mPosterGridAdapter = new PosterGridAdapter(this, mMovies.getMovies(), this);
        mRecyclerView.setAdapter(mPosterGridAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_filter:
                String[] filters = getResources().getStringArray(R.array.filter_alert_options);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.filter_alert_title);
                builder.setSingleChoiceItems(filters, mSelectedSort, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item)
                        {
                            case 0:
                                configurePosterGridView(false, false);
                                mSelectedSort = 0;
                                mMovies.clear();
                                getMovies(NetworkUtils.SORT_POPULAR);
                                break;
                            case 1:
                                configurePosterGridView(false, false);
                                mSelectedSort = 1;
                                mMovies.clear();
                                getMovies(NetworkUtils.SORT_TOP_RATED);
                                break;
                        }
                        mFilterSelectionAlertDialog.dismiss();
                    }
                });
                mFilterSelectionAlertDialog = builder.create();
                mFilterSelectionAlertDialog.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
