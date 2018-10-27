package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies.MainActivity.SELECTED_MOVIE;

public class DetailActivity extends AppCompatActivity {

    public final static String sBaseUrl = "http://image.tmdb.org/t/p/w780/";
    private Menu mMenu;
    private ScrollView mScrollView;
    private int mOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrientation = getResources().getConfiguration().orientation;

        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_detail_landscape);
            LinearLayout mainFrame = findViewById(R.id.layout_detail);
            mainFrame.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        }
        else {
            setContentView(R.layout.activity_detail);
            RelativeLayout mainFrame = findViewById(R.id.layout_detail);
            mainFrame.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        }

        Movie movie = (Movie) getIntent().getSerializableExtra(SELECTED_MOVIE);
        setTitle(movie.getTitle());

        mScrollView = findViewById(R.id.sv_detail);
        ImageView iv = findViewById(R.id.iv_poster);

        Picasso.get().load(sBaseUrl + movie.getPosterPath())
            .error(R.drawable.ic_broken_image_black_12dp)
            .fit()
            .into(iv);

        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        String releaseDate = movie.getReleaseDate() + "\n";
        releaseDateTextView.setText(releaseDate);

        TextView userRatingTextView = findViewById(R.id.tv_user_rating);
        String userRating = movie.getUserRating() + "/10\n";
        userRatingTextView.setText(userRating);

        TextView overviewTextView = findViewById(R.id.tv_overview);
        String overview = movie.getOverview() + "\n";
        overviewTextView.setText(overview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mOrientation == Configuration.ORIENTATION_PORTRAIT) {
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
}
