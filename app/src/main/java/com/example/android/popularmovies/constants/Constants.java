package com.example.android.popularmovies.constants;

public class Constants {

    /**
     * API_KEY --- ADD API KEY HERE
     */
    public final static String KEY_API = "";

    /**
     * URL COMPONENTS
     */
    public final static String URL_BASE = "https://api.themoviedb.org/3/movie/";
    public final static String URL_BASE_IMAGE = "http://image.tmdb.org/t/p/w342/";
    public final static String URL_PARAM_API_KEY = "api_key";
    public final static String URL_PARAM_LANGUAGE = "language";
    public final static String URL_PARAM_PAGE = "page";
    public final static String URL_LANG_ENG = "en-US";

    /**
     * SORT VALUES
     */
    public final static String SORT_POPULAR = "popular";
    public final static String SORT_TOP_RATED = "top_rated";
    public final static String SORT_FAVORITE = "favorite";

    /**
     * DB COMPONENTS
     */
    public final static String DB_NAME = "popularMovies";
    public final static String QUERY_FAVORITES = "SELECT * FROM favorite_movies ORDER BY title";
    public final static String QUERY_FAVORITE_BY_ID = "SELECT * FROM favorite_movies WHERE id = :id";

    /**
     * JSON COMPONENTS
     */
    public static final String JSON_KEY_RESULTS = "results";
    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_TITLE = "title";
    public static final String JSON_KEY_POSTER_PATH = "poster_path";
    public static final String JSON_KEY_OVERVIEW = "overview";
    public static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    public static final String JSON_KEY_RELEASE_DATE = "release_date";

    /**
     * INTENT TAGS
     */
    public static final String EXTRA_SELECTED_SORT = "EXTRA_SELECTED_SORT";
    public static final String EXTRA_SELECTED_MOVIE = "EXTRA_SELECTED_MOVIE";
    public static final String EXTRA_MOVIES = "EXTRA_MOVIES";
    public static final String EXTRA_PAGE_NUM = "EXTRA_PAGE_NUM";
}
