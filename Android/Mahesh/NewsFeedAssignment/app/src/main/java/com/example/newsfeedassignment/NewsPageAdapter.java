package com.example.newsfeedassignment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;

public class NewsPageAdapter extends FragmentStatePagerAdapter {

    public static final int NEWS_SITE_COUNT;
    private static final HashMap<Integer, String> newsSiteNames;

    static {
        newsSiteNames = new HashMap<Integer, String>();
        newsSiteNames.put(0,"bbc");
        newsSiteNames.put(1,"cnn");
        NEWS_SITE_COUNT = newsSiteNames.size();
    }

    public NewsPageAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int i) {

        return new NewsFragment(i);
    }

    @Override
    public int getCount() {
        return NEWS_SITE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String newsSiteName;
        if (position >= 0 && position < NEWS_SITE_COUNT - 1)
        {
            newsSiteName = newsSiteNames.get(position);
        }
        else
        {
            newsSiteName = newsSiteNames.get(NEWS_SITE_COUNT - 1);
        }

        return newsSiteName;
    }
}