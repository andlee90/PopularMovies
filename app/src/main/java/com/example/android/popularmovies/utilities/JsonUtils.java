package com.example.android.popularmovies.utilities;

import android.content.Context;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.popularmovies.constants.Constants.JSON_KEY_ID;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_OVERVIEW;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_POSTER_PATH;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_RELEASE_DATE;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_RESULTS;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_TITLE;
import static com.example.android.popularmovies.constants.Constants.JSON_KEY_VOTE_AVERAGE;

public class JsonUtils {

    private Context mContext;
    public JsonUtils(Context context) {
        mContext = context;
    }

    public ArrayList<Movie> parseMoviesFromJson(String json) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonFull = new JSONObject(json.trim());
            JSONArray jsonResults = jsonFull.optJSONArray(JSON_KEY_RESULTS);
            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject obj = jsonResults.optJSONObject(i);
                Movie movie = new Movie(
                        obj.optString(JSON_KEY_ID, mContext.getResources().getString(R.string.fallback_id)),
                        obj.optString(JSON_KEY_TITLE, mContext.getResources().getString(R.string.fallback_title)),
                        obj.optString(JSON_KEY_POSTER_PATH, mContext.getResources().getString(R.string.fallback_poster_path)),
                        obj.optString(JSON_KEY_OVERVIEW, mContext.getResources().getString(R.string.fallback_overview)),
                        obj.optString(JSON_KEY_VOTE_AVERAGE, mContext.getResources().getString(R.string.fallback_vote_average)),
                        obj.optString(JSON_KEY_RELEASE_DATE, mContext.getResources().getString(R.string.fallback_release_date)));

                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
