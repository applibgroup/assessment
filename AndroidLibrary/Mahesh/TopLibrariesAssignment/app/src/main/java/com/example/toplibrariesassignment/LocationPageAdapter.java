package com.example.toplibrariesassignment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.HashMap;

public class LocationPageAdapter extends FragmentStateAdapter {

    public static final int CITIES_COUNT;
    private static final HashMap<Integer, String> cityName;

    static {
        cityName = new HashMap<Integer, String>();
        cityName.put(0,"kochi");
        cityName.put(1,"chennai");
        CITIES_COUNT = cityName.size();
    }

    public LocationPageAdapter(FragmentActivity activity) {

        super(activity);

    }


    @Override
    public Fragment createFragment(int i) {

        return new LocationsFragment(i);
    }

    @Override
    public int getItemCount() {
        return CITIES_COUNT;
    }

}