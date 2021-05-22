package com.example.newsapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPageAdapter extends FragmentStatePagerAdapter {



    public MyPageAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int i) {
        // Log.e("pra"," "+i);
        fragment frag = new fragment(i);
        return frag;
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
