package com.pwn3rzs.crto.android.adapter.headerCollapsed;


import com.pwn3rzs.crto.android.activity.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HeaderCollapsedObject implements ViewModel {

    public final String title;
    public final Integer icon;

    public HeaderCollapsedObject(String name, Integer icon) {
        this.title = name;
        this.icon = icon;
    }


    public final List<ViewModel> list = new ArrayList<>();
}

