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
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.SortUtils;

public class MainActivity extends AppCompatActivity implements PosterGridAdapter.GridItemClickListener, AsyncResult {

    private static final String SELECTED_SORT = "SELECTED_SORT";
    public static final String SELECTED_MOVIE = "SELECTED_MOVIE";
    private static final String MOVIES = "MOVIES";
    private static final String PAGE_NUM = "PAGE_NUM";

    private int mSelectedSortValue;
    private int mPageNum;
    private Movies mMovies;
    private ProgressBar mProgressBar;
    private TextView mResponse;
    private RecyclerView mRecyclerView;
    private PosterGridAdapter mPosterGridAdapter;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    private AlertDialog mFilterSelectionAlertDialog;
    private JsonUtils mJsonUtils = new JsonUtils(this);

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mMovies = (Movies) savedInstanceState.getSerializable(MOVIES);
            mSelectedSortValue = savedInstanceState.getInt(SELECTED_SORT);
            mPageNum = savedInstanceState.getInt(PAGE_NUM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_SORT, mSelectedSortValue);
        outState.putSerializable(MOVIES, mMovies);
        outState.putInt(PAGE_NUM, mPageNum);
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

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 3;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mEndlessScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPageNum++;
                fetchMovieData(SortUtils.getApiValue(mSelectedSortValue), mPageNum);
            }
        };

        mRecyclerView.addOnScrollListener(mEndlessScrollListener);

        if (savedInstanceState != null) {
            mSelectedSortValue = savedInstanceState.getInt(SELECTED_SORT);
            mMovies = (Movies) savedInstanceState.getSerializable(MOVIES);
            mPageNum = savedInstanceState.getInt(PAGE_NUM);
            setupUI(true, true);

        } else {
            mSelectedSortValue = 0;
            mPageNum = 1;
            mMovies = new Movies();
            fetchMovieData(SortUtils.getApiValue(mSelectedSortValue), mPageNum);
            setupUI(false, false);
        }

        mPosterGridAdapter = new PosterGridAdapter(this, mMovies.getMovies(), this);
        mRecyclerView.setAdapter(mPosterGridAdapter);
    }

    public void setupUI(boolean loadFinished, boolean hasResults) {
        if(!loadFinished && !hasResults) {
            mProgressBar.setVisibility(View.VISIBLE);
            mResponse.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

        } else if(loadFinished && !hasResults) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mResponse.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mResponse.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void resetUI(int sort) {
        mMovies.clear();
        mPosterGridAdapter.notifyDataSetChanged();
        mEndlessScrollListener.resetState();
        fetchMovieData(SortUtils.getApiValue(mSelectedSortValue = sort), mPageNum = 1);
        setupUI(false, false);
    }

    private void fetchMovieData(String sort, int pageNum) {
        new FetchMovieDataTask(this, sort, pageNum).execute();
    }

    @Override
    public void onTaskFinish(String output) {
        if (output != null) {
            int count = mMovies.getMovieCount();
            mMovies.addAll(mJsonUtils.parseMoviesFromJson(output));
            mPosterGridAdapter.notifyItemRangeInserted(count, 20);
            setupUI(true, true);

        } else {
            // No response from server, notify user
            setupUI(true, false);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(SELECTED_MOVIE, mPosterGridAdapter.getItem(clickedItemIndex));
        startActivity(intent);
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
                builder.setSingleChoiceItems(filters, mSelectedSortValue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                resetUI(0);
                                break;
                            case 1:
                                resetUI(1);
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
