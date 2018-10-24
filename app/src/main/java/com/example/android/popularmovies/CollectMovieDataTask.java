package com.example.android.popularmovies;

import android.os.AsyncTask;

import java.io.IOException;

import static com.example.android.popularmovies.utilities.NetworkUtils.*;

public class CollectMovieDataTask extends AsyncTask<Void, Void, String> {

    private AsyncResult mAsyncResult;
    private String mSortBy;
    private int mPageNum;

    CollectMovieDataTask(AsyncResult mAsyncResult, String sortBy, int pageNum) {
        this.mAsyncResult = mAsyncResult;
        this.mSortBy = sortBy;
        this.mPageNum = pageNum;
    }

    @Override
    protected String doInBackground(Void... v) {
        StringBuilder response = new StringBuilder();
        try {
            response.append(getResponseFromHttpUrl(buildUrl(mSortBy, mPageNum)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        mAsyncResult.onTaskFinish(result);
    }
}
