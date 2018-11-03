package com.example.android.popularmovies.ui.details.trailers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.async.AsyncResult;
import com.example.android.popularmovies.async.FetchMovieDataTask;
import com.example.android.popularmovies.constants.Constants;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.Trailer;
import com.example.android.popularmovies.utilities.JsonUtils;

import java.util.ArrayList;

import static com.example.android.popularmovies.constants.Constants.ARG_MOVIE;
import static com.example.android.popularmovies.utilities.NetworkUtils.buildUrlById;

public class TrailersFragment extends ListFragment implements AsyncResult {

    private Movie mMovie;
    private JsonUtils mJsonUtils;

    private ProgressBar mProgressBar;
    private TextView mTextView;

    public TrailersFragment() {}

    public static TrailersFragment newInstance(Movie movie) {
        TrailersFragment trailersFragment = new TrailersFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE, movie);
        trailersFragment.setArguments(bundle);

        return trailersFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mJsonUtils = new JsonUtils(getActivity());

        if(getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);

        } if(mMovie != null) {
            fetchMovieData();
        }

        View rootView = inflater.inflate(R.layout.fragment_trailers, container, false);
        mTextView = rootView.findViewById(R.id.tv_empty);
        mProgressBar = rootView.findViewById(R.id.pb_loading);

        return rootView;
    }

    private void fetchMovieData() {
        new FetchMovieDataTask(this, buildUrlById(mMovie.getId(),
                Constants.URL_ENDPOINT_TRAILERS), Constants.RESPONSE_TRAILERS).execute();
    }

    @Override
    public void onTaskFinish(String output) {
        ArrayList<Trailer> trailers = mJsonUtils.parseTrailersFromJson(output);

        if(trailers == null || trailers.isEmpty()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTextView.setVisibility(View.VISIBLE);

        } else {
            TrailerListAdapter trailerListAdapter = new TrailerListAdapter(trailers, getActivity());
            setListAdapter(trailerListAdapter);
        }
    }
}
