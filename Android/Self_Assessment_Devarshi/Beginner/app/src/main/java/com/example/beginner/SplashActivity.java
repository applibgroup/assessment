package com.example.beginner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private SliderAdapter mAdapter;
    private ViewPager2 viewPager2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewPager2=findViewById(R.id.viewPager);
        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.image1,getString(R.string.heading1),getString(R.string.description1)));
        sliderItems.add(new SliderItem(R.drawable.image2,getString(R.string.heading2),getString(R.string.description2)));
        sliderItems.add(new SliderItem(R.drawable.image3,getString(R.string.heading3),getString(R.string.description3)));
        mAdapter=new SliderAdapter(sliderItems,viewPager2);
        viewPager2.setAdapter(mAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(50));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        mAdapter.setOnItemClickListener(new SliderAdapter.OnItemClickListener() {
            @Override
            public void onNextClick(int position) {
                if(position!=2)
                {
                    viewPager2.setCurrentItem(position+1,true);
                }
                else{
                    Intent intent = new Intent(SplashActivity.super.getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onSkipClick(int position) {
                Intent intent = new Intent(SplashActivity.super.getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
}
}
