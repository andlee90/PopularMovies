package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String sBaseUrl = "https://api.themoviedb.org/3/movie/";
    private final static String sParamAPIKey = "api_key";
    private final static String sParamLanguage = "language";
    private final static String sParamPage = "page";
    private final static String sLanguage = "en-US";

    // TODO: Relocate/Remove API Key
    private final static String sAPIKey = "";

    public final static String SORT_POPULAR = "popular";
    public final static String SORT_TOP_RATED = "top_rated";

    public static URL buildUrl(String sortBy, int page) {
        Uri builtUri = Uri.parse(sBaseUrl + sortBy).buildUpon()
                .appendQueryParameter(sParamAPIKey, sAPIKey)
                .appendQueryParameter(sParamLanguage, sLanguage)
                .appendQueryParameter(sParamPage, Integer.toString(page))
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