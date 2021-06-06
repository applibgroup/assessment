package com.applibgroup.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.applibgroup.contactapp.db.AppDatabase;
import com.applibgroup.contactapp.db.User;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase instance = null;
        instance = AppDatabase.getINSTANCE(this);

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d("UPDATE 5 Seconds", "run: 5 seconds");
                AllFragment.updateUserList(getApplicationContext());
            }
        }, 500, TimeUnit.MILLISECONDS);

        Paper.init(getApplicationContext());

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        final MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        adapter.addItem(FavouritesFragment.newInstance("Favourites","0"),"Favourites");
        adapter.addItem(AllFragment.newInstance("All","1"),"All");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(AllFragment.userList.isEmpty())
                    AllFragment.updateUserList(getApplicationContext());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private class MainAdapter extends FragmentPagerAdapter {

        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        public void addItem ( Fragment fragment, String title){
            titleList.add(title);
            fragmentList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
