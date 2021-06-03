package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPageAdapter extends FragmentStatePagerAdapter {



    public MyPageAdapter(FragmentManager fm, int behaviour) {

        super(fm, behaviour);

    }


    @NonNull
    @Override
    public Fragment getItem(int i) {
        return new fragment(i);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0)
            return "India";
        else if(position == 1)
            return "World";
        else
            return "hello";


    }
}
