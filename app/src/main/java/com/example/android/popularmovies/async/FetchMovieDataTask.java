package com.example.android.popularmovies.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import static com.example.android.popularmovies.utilities.NetworkUtils.*;

public class FetchMovieDataTask extends AsyncTask<Void, Void, String> {

    private AsyncResult mAsyncResult;
    private URL mUrl;
    private String mResponseType;

    public FetchMovieDataTask(AsyncResult mAsyncResult, URL url, String type) {
        this.mAsyncResult = mAsyncResult;
        this.mUrl = url;
        this.mResponseType = type;
    }

    @Override
    protected String doInBackground(Void... v) {
        StringBuilder response = new StringBuilder();
        response.append(mResponseType);
        try {
            response.append(getResponseFromHttpUrl(mUrl));
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
