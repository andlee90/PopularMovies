package com.example.android.popularmovies.ui.details.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Review;

import java.util.ArrayList;

public class ReviewListAdapter  extends ArrayAdapter<Review> {

    private static class ViewHolder {
        TextView authorTextView;
        TextView contentTextView;
    }

    ReviewListAdapter(ArrayList<Review> reviews, Context context) {
        super(context, R.layout.trailer_list_item, reviews);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Review review = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);
            viewHolder.authorTextView = convertView.findViewById(R.id.tv_author);
            viewHolder.contentTextView = convertView.findViewById(R.id.tv_content);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(review != null) {
            viewHolder.authorTextView.setText(review.getAuthor());
            viewHolder.contentTextView.setText(review.getContent());
        }

        return convertView;
    }
}
