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
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0)
            return "General";
        else if(position == 2)
            return "Sports";
        else if(position == 4)
            return "Business";
        else if(position == 3)
            return "Technology";
        else if(position == 1)
            return "Entertainment";
        else if(position == 5)
            return "Science";
        else if(position == 6)
            return "Health";
        else
            return "hello";


    }
}
