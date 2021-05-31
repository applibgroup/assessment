package com.example.androidsplashscreenassignment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 3 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new SplashScreenFragment(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    /* Button OnClick Methods */
    public void onClickNext(View view)
    {
        int currentItem = viewPager.getCurrentItem();
        // Next button will go to the next page, except in the last page case
        if (currentItem < NUM_PAGES - 1)
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
        // Otherwise, the process continues
        else
        {
            goToFirstActivity();
        }
    }
    public void onClickBack(View view)
    {
        onBackPressed();
    }
    public void onClickSkip(View view)
    {
        // The Activity is skipped, so the first activity after splash screen is called
        goToFirstActivity();
    }
    private void goToFirstActivity()
    {
        Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(goToNextActivity);
    }
}
