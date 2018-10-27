package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterGridAdapter extends RecyclerView.Adapter<PosterGridAdapter.PosterViewHolder> {

    private final static String sBaseUrl = "http://image.tmdb.org/t/p/w342/";

    private ArrayList<Movie> mMovies;
    private LayoutInflater mInflater;
    private final GridItemClickListener mOnClickListener;

    PosterGridAdapter(Context context, ArrayList<Movie> movies, GridItemClickListener clickListener) {
        this.mMovies = movies;
        this.mInflater = LayoutInflater.from(context);
        this.mOnClickListener = clickListener;
    }

    public interface GridItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_poster_grid_item, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        //Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(sBaseUrl + mMovies.get(position).getPosterPath())
                .error(R.drawable.ic_broken_image_black_12dp)
                .noPlaceholder()
                .into(holder.getPosterImageView());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    Movie getItem(int id) {
        return mMovies.get(id);
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private ImageView posterImageView;

        PosterViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_item_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        ImageView getPosterImageView() {
            return posterImageView;
        }
    }
}
