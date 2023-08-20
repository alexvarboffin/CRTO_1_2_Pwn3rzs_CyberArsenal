package com.pwn3rzs.crto.android.activity.appitem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pwn3rzs.crto.android.R;
import com.pwn3rzs.crto.android.adapter.ResType;

import pokercc.android.expandablerecyclerview.ExpandableAdapter;


public class AppViewHolder extends ExpandableAdapter.ViewHolder {

    public ImageView installedIcon;

    public TextView titleTextView;
    public TextView rateTextView;
    public TextView countTextView;

    public AppViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.text_title);
        rateTextView = itemView.findViewById(R.id.text_rate);
        countTextView = itemView.findViewById(R.id.text_count);
        installedIcon = itemView.findViewById(R.id.icon);
    }

    public void bind(SimpleLine app, int childPosition) {
        titleTextView.setText(app.shortName);
        rateTextView.setText(app.getRate());
        countTextView.setText(app.getCount());
        if (app.resType == ResType.IMAGES) {
            installedIcon.setImageResource(R.mipmap.ic_launcher);
        } else if (app.resType == ResType.DIR) {
            installedIcon.setImageResource(R.drawable.ic_folder_blue_36dp);
        } else {
            installedIcon.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
