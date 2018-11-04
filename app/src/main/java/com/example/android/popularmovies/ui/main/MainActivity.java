package com.example.android.popularmovies.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.async.AppExecutors;
import com.example.android.popularmovies.async.AsyncResult;
import com.example.android.popularmovies.async.FetchMovieDataTask;
import com.example.android.popularmovies.constants.Constants;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.ui.details.DetailActivity;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.SortUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.constants.Constants.EXTRA_MOVIES;
import static com.example.android.popularmovies.constants.Constants.EXTRA_PAGE_NUM;
import static com.example.android.popularmovies.constants.Constants.EXTRA_SELECTED_MOVIE;
import static com.example.android.popularmovies.constants.Constants.EXTRA_SELECTED_SORT;
import static com.example.android.popularmovies.utilities.NetworkUtils.buildDefaultUrl;

public class MainActivity extends AppCompatActivity implements PosterGridAdapter.GridItemClickListener, AsyncResult, Button.OnClickListener {

    private int mSelectedSortValue;
    private int mPageNum;
    private ArrayList<Movie> mMovies;
    private ProgressBar mProgressBar;
    private LinearLayout mResponse;
    private TextView mEmpty;
    private Button mViewFavoritesButton;
    private EmptySafeRecyclerView mRecyclerView;
    private PosterGridAdapter mPosterGridAdapter;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    private AlertDialog mFilterSelectionAlertDialog;
    private AppDatabase mDb;
    private JsonUtils mJsonUtils = new JsonUtils(this);

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
            mSelectedSortValue = savedInstanceState.getInt(EXTRA_SELECTED_SORT);
            mPageNum = savedInstanceState.getInt(EXTRA_PAGE_NUM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_SELECTED_SORT, mSelectedSortValue);
        outState.putParcelableArrayList(EXTRA_MOVIES, mMovies);
        outState.putInt(EXTRA_PAGE_NUM, mPageNum);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mResponse = findViewById(R.id.tv_check_connection);
        mProgressBar = findViewById(R.id.pb_loading);
        mRecyclerView = findViewById(R.id.rv_main);
        mEmpty = findViewById(R.id.tv_no_favorites);
        mViewFavoritesButton = findViewById(R.id.btn_view_favorites);
        mViewFavoritesButton.setOnClickListener(this);

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 3;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(500);
        mRecyclerView.setEmptyView(mResponse);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mEndlessScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPageNum++;
                if(mSelectedSortValue != 2) fetchMovieData(SortUtils.getApiValue(mSelectedSortValue), mPageNum);
            }
        };

        mRecyclerView.addOnScrollListener(mEndlessScrollListener);

        if (savedInstanceState != null) {
            mSelectedSortValue = savedInstanceState.getInt(EXTRA_SELECTED_SORT);
            mMovies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
            mPageNum = savedInstanceState.getInt(EXTRA_PAGE_NUM);

        } else {
            mSelectedSortValue = 0;
            mPageNum = 1;
            mMovies = new ArrayList<>();
            fetchMovieData(SortUtils.getApiValue(mSelectedSortValue), mPageNum);
            mRecyclerView.setEmptyView(mProgressBar);
        }

        mPosterGridAdapter = new PosterGridAdapter(this, mMovies, this);
        mRecyclerView.setAdapter(mPosterGridAdapter);
        setupViewModel();
    }

    private void resetUI() {
        if(mSelectedSortValue == 2) {
            fetchFavoriteMovieData();
            mRecyclerView.setEmptyView(mEmpty);

        } else {
            mEndlessScrollListener.resetState();
            mPosterGridAdapter.clearMovies();
            fetchMovieData(SortUtils.getApiValue(mSelectedSortValue), mPageNum = 1);
            mRecyclerView.setEmptyView(mProgressBar);
        }
    }

    private void fetchMovieData(String sort, int pageNum) {
        new FetchMovieDataTask(this, buildDefaultUrl(sort, pageNum),
                Constants.RESPONSE_MOVIES).execute();
    }

    private void fetchFavoriteMovieData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Movie> movieEntries = (ArrayList<Movie>) mDb.favoriteMovieDao().loadAllFavoriteMoviesAsList();
                mPosterGridAdapter.setMovies(movieEntries);
            }
        });
    }

    @Override
    public void onTaskFinish(String output) {
        if (output != null) {
            mPosterGridAdapter.addMovies(mJsonUtils.parseMoviesFromJson(output));

        } else {
            // No response from server, notify user
            mRecyclerView.setEmptyView(mResponse);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_SELECTED_MOVIE, mPosterGridAdapter.getMovie(clickedItemIndex));
        startActivity(intent);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(mSelectedSortValue == 2) {
                    mPosterGridAdapter.setMovies((ArrayList<Movie>)movies);
                }
            }
        });
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
                buildFilterDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildFilterDialog() {
        String[] filters = getResources().getStringArray(R.array.filter_alert_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.filter_alert_title);
        builder.setSingleChoiceItems(filters, mSelectedSortValue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        mSelectedSortValue = 0;
                        break;
                    case 1:
                        mSelectedSortValue = 1;
                        break;
                    case 2:
                        mSelectedSortValue = 2;
                        break;
                }
                resetUI();
                mFilterSelectionAlertDialog.dismiss();
            }
        });
        mFilterSelectionAlertDialog = builder.create();
        mFilterSelectionAlertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == mViewFavoritesButton) {
            mSelectedSortValue = 2;
            resetUI();
        }
    }
}
