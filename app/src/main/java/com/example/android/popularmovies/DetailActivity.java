package com.example.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.popularmovies.async.AppExecutors;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.FavoriteMovie;
import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.popularmovies.MainActivity.SELECTED_MOVIE;

public class DetailActivity extends AppCompatActivity {

    public final static String sBaseUrl = "http://image.tmdb.org/t/p/w780/";
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

        mDb = AppDatabase.getInstance(getApplicationContext());
        setUpUI();
        checkIfFavorite();
    }

    private void setUpUI() {
        mMovie = (Movie) getIntent().getSerializableExtra(SELECTED_MOVIE);
        setTitle(mMovie.getTitle());

        mScrollView = findViewById(R.id.sv_detail);
        ImageView iv = findViewById(R.id.iv_poster);
        mFavoriteImageView = findViewById(R.id.iv_favorite);

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteButtonClicked();
            }
        });

        Picasso.get().load(sBaseUrl + mMovie.getPosterPath())
                .error(R.drawable.ic_broken_image_black_12dp)
                .fit()
                .into(iv);

        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        String releaseDate = mMovie.getReleaseDate() + "\n";
        releaseDateTextView.setText(releaseDate);

        TextView userRatingTextView = findViewById(R.id.tv_user_rating);
        String userRating = mMovie.getUserRating() + "/10\n";
        userRatingTextView.setText(userRating);

        TextView overviewTextView = findViewById(R.id.tv_overview);
        String overview = mMovie.getOverview() + "\n";
        overviewTextView.setText(overview);
    }

    private void onFavoriteButtonClicked() {
        if(mIsFavorite) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteMovieDao().deleteFavoriteMovie(new FavoriteMovie(mMovie.getId(), mMovie.getTitle()));
                }
            });
        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteMovieDao().insertFavoriteMovie(new FavoriteMovie(mMovie.getId(), mMovie.getTitle()));
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
                }
                else {
                    mMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_visibility));
                    mScrollView.setVisibility(View.VISIBLE);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkIfFavorite() {
        final LiveData<List<FavoriteMovie>> favoriteMovies = mDb.favoriteMovieDao().loadAllFavoriteMovies();
        favoriteMovies.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                if(favoriteMovies != null){
                    for(FavoriteMovie m: favoriteMovies) {
                        if(m.getId().equals(mMovie.getId())) {
                            mFavoriteImageView.setImageResource(R.drawable.ic_action_favorite_on);
                            mIsFavorite = true;
                            break;
                        } else {
                            mFavoriteImageView.setImageResource(R.drawable.ic_action_favorite_off);
                            mIsFavorite = false;
                        }
                    }
                }
            }
        });
    }
}
