package com.example.android.popularmovies.utilities;

import android.content.Context;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.popularmovies.constants.Constants.*;

public class JsonUtils {

    private Context mContext;

    public JsonUtils(Context context) {
        mContext = context;
    }

    public ArrayList<Movie> parseMoviesFromJson(String json) {

        if(json == null) return null;

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            // Remove response tag
            json = json.substring(4);
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

    public ArrayList<Review> parseReviewsFromJson(String json) {

        if(json == null) return null;

        ArrayList<Review> reviews = new ArrayList<>();

        try {
            // Remove response tag
            json = json.substring(4);
            JSONObject jsonFull = new JSONObject(json.trim());
            JSONArray jsonResults = jsonFull.optJSONArray(JSON_KEY_RESULTS);
            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject obj = jsonResults.optJSONObject(i);
                Review review = new Review(
                        obj.optString(JSON_KEY_AUTHOR, mContext.getResources().getString(R.string.fallback_author)),
                        obj.optString(JSON_KEY_CONTENT, mContext.getResources().getString(R.string.fallback_content)));

                reviews.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public ArrayList<Trailer> parseTrailersFromJson(String json) {

        if(json == null) return null;
        ArrayList<Trailer> trailers = new ArrayList<>();

        try {
            // Remove response tag
            json = json.substring(4);
            JSONObject jsonFull = new JSONObject(json.trim());
            JSONArray jsonResults = jsonFull.optJSONArray(JSON_KEY_RESULTS);
            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject obj = jsonResults.optJSONObject(i);
                Trailer trailer = new Trailer(
                        obj.optString(JSON_KEY_ID, mContext.getResources().getString(R.string.fallback_id)),
                        obj.optString(JSON_KEY_KEY, mContext.getResources().getString(R.string.fallback_key)),
                        obj.optString(JSON_KEY_NAME, mContext.getResources().getString(R.string.fallback_name)),
                        obj.optString(JSON_KEY_SITE, mContext.getResources().getString(R.string.fallback_site)),
                        obj.optString(JSON_KEY_SIZE, mContext.getResources().getString(R.string.fallback_size)),
                        obj.optString(JSON_KEY_TYPE, mContext.getResources().getString(R.string.fallback_type)));

                trailers.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }
}
