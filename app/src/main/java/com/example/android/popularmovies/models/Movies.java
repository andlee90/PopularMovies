package com.example.android.popularmovies.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Movies implements Serializable {
    private ArrayList<Movie> movies = new ArrayList<>();

    public Movies() {}

    public Movies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public boolean isEmpty() {
        return movies.isEmpty();
    }

    public void addAll(ArrayList<Movie> movies) {
        this.movies.addAll(movies);
    }

    public void clear() {
        this.movies.clear();
    }

    public int getMovieCount() {
        return movies.size();
    }
}
