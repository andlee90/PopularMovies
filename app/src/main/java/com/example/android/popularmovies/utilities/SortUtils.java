package com.example.android.popularmovies.utilities;

import android.util.SparseArray;

import static com.example.android.popularmovies.constants.Constants.SORT_FAVORITE;
import static com.example.android.popularmovies.constants.Constants.SORT_POPULAR;
import static com.example.android.popularmovies.constants.Constants.SORT_TOP_RATED;

public class SortUtils {

    private static SparseArray<String> sortData;

    static {
        sortData = new SparseArray<>();
        sortData.append(0, SORT_POPULAR);
        sortData.append(1, SORT_TOP_RATED);
        sortData.append(3, SORT_FAVORITE);
    }

    public static String getApiValue(int filterValue) {
        return sortData.get(filterValue);
    }
}
