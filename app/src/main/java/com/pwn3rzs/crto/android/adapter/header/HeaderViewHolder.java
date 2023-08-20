package com.pwn3rzs.crto.android.adapter.header;

import android.view.View;
import android.widget.TextView;


import com.pwn3rzs.crto.android.R;

import pokercc.android.expandablerecyclerview.ExpandableAdapter;

public class HeaderViewHolder extends ExpandableAdapter.ViewHolder {

    private final TextView textView;

    public HeaderViewHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.textView);
    }

    public void bind(HeaderObject tmp) {
        textView.setText(tmp.title);
        if (tmp.icon != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(tmp.icon, 0, 0, 0);
        }
    }
}