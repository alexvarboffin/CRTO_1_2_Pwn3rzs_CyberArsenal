package com.pwn3rzs.crto.android.activity;


import android.content.res.AssetManager;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pwn3rzs.crto.android.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private final List<String> indexToPage = new ArrayList<>();
    private ViewPagerAdapter mPagerAdapter;
    private AssetManager assetManager;
    private BottomNavigationView bottomNavigationView;
    public static String base = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager2 = findViewById(R.id.mainViewPager);
        viewPager2.setUserInputEnabled(false);


        assetManager = getAssets();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        mPagerAdapter = new ViewPagerAdapter(this);

        int j = 0;

        try {
            String[] folders = assetManager.list(base);
            if (folders != null) {
                for (String folder : folders) {
                    Log.d("@@@", "onCreate: " + folder);
                    MenuItem item = bottomNavigationView.getMenu().add(folder);
                    item.setIcon(R.mipmap.ic_launcher);
                    item.setTitle(folder);

                    mPagerAdapter.addFragment(AppListFragment.newInstance(base + "/" + folder), base + "/" + folder);
                    indexToPage.add(folder);
                    ++j;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Integer position = indexToPage.indexOf(item.getTitle());
            if (position >= 0) {
                menuSelected(position);
                return true;
            }
            return false;
        });

        viewPager2.setAdapter(mPagerAdapter);
        viewPager2.setOffscreenPageLimit(5);
    }

    private void menuSelected(Integer position) {
        if (viewPager2.getCurrentItem() != position) {
            viewPager2.setCurrentItem(position);
        }
    }
}

