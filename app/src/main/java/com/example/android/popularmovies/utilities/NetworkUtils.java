package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.popularmovies.constants.Constants.KEY_API;
import static com.example.android.popularmovies.constants.Constants.URL_BASE;
import static com.example.android.popularmovies.constants.Constants.URL_LANG_ENG;
import static com.example.android.popularmovies.constants.Constants.URL_PARAM_API_KEY;
import static com.example.android.popularmovies.constants.Constants.URL_PARAM_LANGUAGE;
import static com.example.android.popularmovies.constants.Constants.URL_PARAM_PAGE;

public class NetworkUtils {

    public static URL buildUrl(String sortBy, int page) {
        Uri builtUri = Uri.parse(URL_BASE + sortBy).buildUpon()
                .appendQueryParameter(URL_PARAM_API_KEY, KEY_API)
                .appendQueryParameter(URL_PARAM_LANGUAGE, URL_LANG_ENG)
                .appendQueryParameter(URL_PARAM_PAGE, Integer.toString(page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}