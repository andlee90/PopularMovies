package com.example.android.popularmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "favorite_movies")
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String posterPath;
    private String overview;
    private String userRating;
    private String releaseDate;

    public Movie(@NonNull String id, String title, String posterPath, String overview,
                 String userRating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = formatDate(releaseDate);
    }

    @Ignore
    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    @Ignore
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
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

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
    }
}
