package com.example.android.popularmovies.ui.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.ui.details.reviews.ReviewsFragment;
import com.example.android.popularmovies.ui.details.trailers.TrailersFragment;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter{

    private Context mContext;
    private Movie mMovie;

    DetailFragmentPagerAdapter(Context context, Movie movie, FragmentManager fm) {
        super(fm);
        mContext = context;
        mMovie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OverviewFragment.newInstance(mMovie);
            case 1:
                return TrailersFragment.newInstance(mMovie);
            case 2:
                return ReviewsFragment.newInstance(mMovie);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_overview);
            case 1:
                return mContext.getString(R.string.tab_trailers);
            case 2:
                return mContext.getString(R.string.tab_reviews);

            default:
                return null;
        }
    }
}
