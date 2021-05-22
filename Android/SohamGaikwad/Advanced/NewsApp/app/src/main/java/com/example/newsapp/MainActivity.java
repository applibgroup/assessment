package com.example.newsapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TabLayout TabLayout;
    private ViewPager viewPager;
    private MyPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        TabLayout = findViewById(R.id.tablayout);


        adapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout.setTabsFromPagerAdapter(adapter);


        // to keep working together of tab layout and view pager
        TabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TabLayout));


        // for top bar of activity
        ActionBar bar = getSupportActionBar();
        bar.setTitle("News");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("black")));

    }



}