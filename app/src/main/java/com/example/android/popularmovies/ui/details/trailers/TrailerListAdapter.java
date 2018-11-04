package com.example.android.popularmovies.ui.details.trailers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.constants.Constants;
import com.example.android.popularmovies.models.Trailer;

import java.util.ArrayList;

public class TrailerListAdapter  extends ArrayAdapter<Trailer> implements View.OnClickListener {

    private Context mContext;
    private Trailer mTrailer;

    private static class ViewHolder {
        TextView nameTextView;
        ImageButton shareImageView;
        RelativeLayout playRelativeLayout;
    }

    TrailerListAdapter(ArrayList<Trailer> trailers, Context context) {
        super(context, R.layout.trailer_list_item, trailers);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        mTrailer = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.trailer_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.tv_name);
            viewHolder.playRelativeLayout = convertView.findViewById(R.id.rv_play);
            viewHolder.playRelativeLayout.setOnClickListener(this);
            viewHolder.shareImageView = convertView.findViewById(R.id.iv_share);
            viewHolder.shareImageView.setOnClickListener(this);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.playRelativeLayout.setTag(position);
        viewHolder.shareImageView.setTag(position);

        if(mTrailer != null) {
            String nameAndSite = mTrailer.getName() + " - " + (mTrailer.getSite().toUpperCase());
            viewHolder.nameTextView.setText(nameAndSite);
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rv_play) {
            Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_BASE_YOUTUBE
                    + mTrailer.getKey()));

            Intent chooser = Intent.createChooser(playIntent,
                    mContext.getString(R.string.play_intent_chooser_title));

            mContext.startActivity(chooser);

        } else if(v.getId() == R.id.iv_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.URL_BASE_YOUTUBE + mTrailer.getKey());

            Intent chooser = Intent.createChooser(shareIntent,
                    mContext.getString(R.string.share_intent_chooser_title));

            mContext.startActivity(chooser);
        }
    }
}
