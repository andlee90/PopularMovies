package com.example.android.popularmovies.activity_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.async.AppExecutors;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies.constants.Constants.EXTRA_SELECTED_MOVIE;
import static com.example.android.popularmovies.constants.Constants.URL_BASE_IMAGE;

public class DetailActivity extends AppCompatActivity {

    private Menu mMenu;
    private ScrollView mScrollView;
    private ImageView mFavoriteImageView;
    private boolean mIsFavorite;
    private Movie mMovie;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getApplicationContext());
        setUpUI();
        checkIfFavorite();
    }

    private void setUpUI() {
        mMovie = getIntent().getParcelableExtra(EXTRA_SELECTED_MOVIE);
        setTitle(mMovie.getTitle());

        mScrollView = findViewById(R.id.sv_detail);
        ImageView iv = findViewById(R.id.iv_poster);
        mFavoriteImageView = findViewById(R.id.iv_favorite);
        mFavoriteImageView.setImageResource(R.drawable.ic_action_favorite_off);

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteButtonClicked();
            }
        });

        Picasso.get().load(URL_BASE_IMAGE + mMovie.getPosterPath())
                .error(R.drawable.ic_broken_image_black_12dp)
                .fit()
                .into(iv);

        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        String releaseDate = mMovie.getReleaseDate();
        releaseDateTextView.setText(releaseDate);

        TextView userRatingTextView = findViewById(R.id.tv_user_rating);
        String userRating = mMovie.getUserRating() + "/10";
        userRatingTextView.setText(userRating);

        TextView overviewTextView = findViewById(R.id.tv_overview);
        String overview = mMovie.getOverview();
        overviewTextView.setText(overview);
    }

    private void onFavoriteButtonClicked() {
        if(mIsFavorite) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteMovieDao().deleteFavoriteMovie(mMovie);
                }
            });

        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteMovieDao().insertFavoriteMovie(mMovie);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getMenuInflater().inflate(R.menu.detail, menu);
            mMenu = menu;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_visibility:
                if(mScrollView.getVisibility() == View.VISIBLE) {
                    mMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_visibility_off));
                    mScrollView.setVisibility(View.INVISIBLE);

                } else {
                    mMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_visibility));
                    mScrollView.setVisibility(View.VISIBLE);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkIfFavorite() {
        DetailViewModelFactory factory = new DetailViewModelFactory(mDb, mMovie.getId());
        final DetailViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movie>() {

            @Override
            public void onChanged(@Nullable Movie movie) {

                if(movie == null) {
                    mFavoriteImageView.setImageResource(R.drawable.ic_action_favorite_off);
                    mIsFavorite = false;

                } else {
                    mFavoriteImageView.setImageResource(R.drawable.ic_action_favorite_on);
                    mIsFavorite = true;
                }
            }
        });
    }
}
