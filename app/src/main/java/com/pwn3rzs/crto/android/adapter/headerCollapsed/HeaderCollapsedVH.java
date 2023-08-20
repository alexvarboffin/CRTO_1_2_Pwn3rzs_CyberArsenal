package com.pwn3rzs.crto.android.adapter.headerCollapsed;

import android.view.View;
import android.widget.TextView;

import com.pwn3rzs.crto.android.R;

import pokercc.android.expandablerecyclerview.ExpandableAdapter;

public class HeaderCollapsedVH extends ExpandableAdapter.ViewHolder {

    public final TextView textView;

    public HeaderCollapsedVH(View view) {
        super(view);
        textView = view.findViewById(R.id.textView);
    }

    public void bind(HeaderCollapsedObject o) {
        textView.setText(o.title);
        if (o.icon != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(o.icon, 0, 0, 0);
        }
    }
}