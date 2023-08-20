package com.pwn3rzs.crto.android.activity.appitem;

import com.pwn3rzs.crto.android.activity.ViewModel;
import com.pwn3rzs.crto.android.adapter.ResType;

public class SimpleLine implements ViewModel {

    public ResType resType = ResType.IMAGES;

    private int id;
    public String url;
    public String fullPath0;
    public String shortName;

    private String rate;
    private String count;
    public boolean isInstalled; // Новое поле

//    public SimpleLine(int id, String url, String title, String rate, String count, boolean isInstalled) {
//        this.id = id;
//        this.url = url;
//        this.fullPath0 = title;
//        this.rate = rate;
//        this.count = count;
//        this.isInstalled = isInstalled;
//    }

    public SimpleLine(String title, String fullPath, ResType type) {
        this.shortName = title;
        this.fullPath0 = fullPath;
        this.resType = type;
    }

    public static boolean isImages(SimpleLine item) {
        return item.fullPath0.endsWith(".jpg") || item.fullPath0.endsWith(".png");
    }

    public static boolean isImages(String fullPath) {
        return fullPath.endsWith(".jpg") || fullPath.endsWith(".png");
    }

    public int getId() {
        return id;
    }


    public String getRate() {
        return rate;
    }

    public String getCount() {
        return count;
    }


}
