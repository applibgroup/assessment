package com.example.newsfeedassignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private com.google.android.material.tabs.TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new NewsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setTabsFromPagerAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ActionBar bar = getSupportActionBar();
        bar.setTitle("News list");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

    }

}