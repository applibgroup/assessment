package com.example.splashscreen3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_PAGES = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:

                fragment = new FirstFragment();//create any fragment

                break;
            case 1:

                fragment = new SecondFragment();//create any fragment
                break;
            // you may add more cases for more fragments
            case 2:
                fragment = new ThirdFragment();

        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        // Show total number of pages.
        return TOTAL_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Fragment1";
        }
        else if(position == 1)  {
            return "Fragment2";
        }
        else{
            return "Fragment3";
        }
    }
}
