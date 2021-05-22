package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager slideVPager;
    private LinearLayout slideLLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideVPager=(ViewPager) findViewById(R.id.slideVPager);
        slideLLayout=(LinearLayout) findViewById(R.id.slideLLayout);

        sliderAdapter=new SliderAdapter(this);
        slideVPager.setAdapter(sliderAdapter);
        addDots(0);
        slideVPager.addOnPageChangeListener(viewListener);
    }
    public void addDots(int position)
    {
        slideLLayout.removeAllViews();
        dots=new TextView[SliderAdapter.slide_headings.length];
        for(int i=0; i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.orange));
            slideLLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.darkorange));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}