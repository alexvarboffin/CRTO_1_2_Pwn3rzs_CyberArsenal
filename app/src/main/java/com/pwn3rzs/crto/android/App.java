package com.pwn3rzs.crto.android;


import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //printHashKey(this);
    }

}
