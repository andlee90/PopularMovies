package com.example.android.popularmovies.utilities;

import android.util.SparseArray;

public class SortUtils {

    private static SparseArray<String> sortData;

    static {
        sortData = new SparseArray<>();
        sortData.append(0, NetworkUtils.SORT_POPULAR);
        sortData.append(1, NetworkUtils.SORT_TOP_RATED);
        sortData.append(3, "Favorite");
    }

    public static String getApiValue(int filterValue) {
        return sortData.get(filterValue);
    }

    public static int getFilterValue(String apiValue) {
        return sortData.keyAt(sortData.indexOfValue(apiValue));
    }
}
