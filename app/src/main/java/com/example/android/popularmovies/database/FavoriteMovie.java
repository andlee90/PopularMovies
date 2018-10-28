package com.example.android.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie {

    @PrimaryKey @NonNull
    private String id;
    private String title;

    public FavoriteMovie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

}
