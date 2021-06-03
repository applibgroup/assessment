package com.example.splashscreen3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager()); //view pager adapter

        viewPager = findViewById(R.id.view_pager_intro);
        viewPager.setAdapter(viewPagerAdapter);
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


    public void moveNext(View view) {
        if (viewPager.getCurrentItem() == 2) {
            return;
        }
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public void moveSkip(View view) {
        viewPager.setCurrentItem(2);
    }

    public void moveBack(View view){
         if (viewPager.getCurrentItem() == 0) {
             return;
         } else {
             viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
         }
     }
}