package com.example.android.popularmovies.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.models.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        movies = database.favoriteMovieDao().loadAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
