package com.example.android.popularmovies.ui.details;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.database.AppDatabase;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final String mId;

    DetailViewModelFactory(AppDatabase database, String id) {
        mDb = database;
        mId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mDb, mId);
    }
}
