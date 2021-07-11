package com.beingprofessional.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

public class InitialActivity extends AppCompatActivity {

    ImageView logo,name,splash_image;
    LottieAnimationView lottieAnimationView;
    private static final int pages=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        splash_image=findViewById(R.id.bg);
        lottieAnimationView=findViewById(R.id.lottie);
        splash_image.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        name.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter{
        public ScreenSlidePagerAdapter(@NonNull FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    page1 tab1 = new page1();
                    return tab1;
                case 1:
                    page2 tab2 = new page2();
                    return tab2;
                case 2:
                    page3 tab3 = new page3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return pages;
        }
    }
}