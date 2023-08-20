package com.pwn3rzs.crto.android.adapter.header;


import com.pwn3rzs.crto.android.activity.ViewModel;

public class HeaderObject implements ViewModel {

    public final String title;
    public final Integer icon;

    public HeaderObject(String name, Integer icon) {
        this.title = name;
        this.icon = icon;
    }
}

