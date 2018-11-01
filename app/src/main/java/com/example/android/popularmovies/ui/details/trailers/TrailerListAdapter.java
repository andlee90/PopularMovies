package com.example.android.popularmovies.ui.details.trailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Trailer;

import java.util.ArrayList;

public class TrailerListAdapter  extends ArrayAdapter<Trailer> {

    private static class ViewHolder {
        TextView nameTextView;
        TextView siteTextView;
    }

    TrailerListAdapter(ArrayList<Trailer> trailers, Context context) {
        super(context, R.layout.trailer_list_item, trailers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Trailer trailer = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.trailer_list_item, parent, false);
            viewHolder.nameTextView = convertView.findViewById(R.id.tv_name);
            viewHolder.siteTextView = convertView.findViewById(R.id.tv_site);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        if(trailer != null) {
            viewHolder.nameTextView.setText(trailer.getName());
            viewHolder.siteTextView.setText(trailer.getSite());
        }

        return result;
    }
}
