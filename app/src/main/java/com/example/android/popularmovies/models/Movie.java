package com.example.android.popularmovies.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Serializable{

    private String title;
    private String posterPath;
    private String overview;
    private String userRating;
    private String releaseDate;

    public Movie(String title, String posterPath, String overview, String userRating, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = formatDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public String getOverview() {
        return overview;
    }
    public String getUserRating() {
        return userRating;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    private String formatDate(String dateString){
        String newDateString;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString);
            newDateString = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(date);
        } catch (ParseException ex) {
            return dateString;
        }
        return newDateString;
    }
}
