package com.example.android.popularmovies.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.popularmovies.async.AppExecutors;

public class EmptySafeRecyclerView extends RecyclerView {

    private View emptyView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptySafeRecyclerView(Context context) {
        super(context);
    }

    public EmptySafeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptySafeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    emptyView.setVisibility(emptyViewVisible ? VISIBLE : INVISIBLE);
                    setVisibility(emptyViewVisible ? INVISIBLE : VISIBLE);
                }
            });
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        if(this.emptyView != null) {
            this.emptyView.setVisibility(INVISIBLE);
        }
        this.emptyView = emptyView;
        checkIfEmpty();
    }
}
