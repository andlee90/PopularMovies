package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.models.Movie;

import java.util.List;

import static com.example.android.popularmovies.constants.Constants.QUERY_FAVORITES;
import static com.example.android.popularmovies.constants.Constants.QUERY_FAVORITE_BY_ID;

@Dao
public interface MovieDao {

    @Query(QUERY_FAVORITES)
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Query(QUERY_FAVORITES)
    List<Movie> loadAllFavoriteMoviesAsList();

    @Insert
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query(QUERY_FAVORITE_BY_ID)
    LiveData<Movie> loadFavoriteMovieById(String id);
}
