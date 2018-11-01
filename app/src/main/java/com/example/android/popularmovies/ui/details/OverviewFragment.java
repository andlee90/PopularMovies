package com.example.android.popularmovies.ui.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Movie;

import static com.example.android.popularmovies.constants.Constants.ARG_MOVIE;

public class OverviewFragment extends Fragment {

    private Movie mMovie;

    public OverviewFragment() {}

    public static OverviewFragment newInstance(Movie movie) {
        OverviewFragment overviewFragment = new OverviewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE, movie);
        overviewFragment.setArguments(bundle);

        return overviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        TextView releaseDateTextView = rootView.findViewById(R.id.tv_release_date);
        TextView overviewTextView = rootView.findViewById(R.id.tv_overview);

        final ImageView[] stars = new ImageView[5];
        stars[0] = rootView.findViewById(R.id.iv_rating_1);
        stars[1] = rootView.findViewById(R.id.iv_rating_2);
        stars[2] = rootView.findViewById(R.id.iv_rating_3);
        stars[3] = rootView.findViewById(R.id.iv_rating_4);
        stars[4] = rootView.findViewById(R.id.iv_rating_5);

        if(getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }

        if(mMovie != null) {
            String releaseDate = mMovie.getReleaseDate();
            releaseDateTextView.setText(releaseDate);

            translateStarRating(Float.parseFloat(mMovie.getUserRating()), stars);

            String overview = mMovie.getOverview();
            overviewTextView.setText(overview);
        }

        return rootView;
    }

    private void translateStarRating(float rating, ImageView[] stars) {

        if(rating < 1.0f) {
            for (ImageView star : stars) {
                star.setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 2.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0) stars[i].setImageResource(R.drawable.ic_display_user_rating_half);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 3.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 4.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else if(i == 1) stars[i].setImageResource(R.drawable.ic_display_user_rating_half);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 5.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 6.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else if(i == 2) stars[i].setImageResource(R.drawable.ic_display_user_rating_half);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 7.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1 || i == 2) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 8.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1 || i == 2) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else if(i == 3) stars[i].setImageResource(R.drawable.ic_display_user_rating_half);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 9.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1 || i == 2 || i == 3) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else if(rating < 10.0f) {
            for(int i = 0; i < stars.length; i++) {
                if(i == 0 || i == 1 || i == 2 || i == 3) stars[i].setImageResource(R.drawable.ic_display_user_rating_full);
                else if(i == 4) stars[i].setImageResource(R.drawable.ic_display_user_rating_half);
                else stars[i].setImageResource(R.drawable.ic_display_user_rating_none);
            }

        } else {
            for (ImageView star : stars) {
                star.setImageResource(R.drawable.ic_display_user_rating_full);
            }
        }
    }
}
