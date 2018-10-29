package com.example.android.popularmovies.activity_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.models.Movie;

public class DetailViewModel extends ViewModel {

    private LiveData<Movie> movie;

    DetailViewModel(AppDatabase database, String id) {
        movie = database.favoriteMovieDao().loadFavoriteMovieById(id);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
