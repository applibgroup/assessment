package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlide;
    private LinearLayout mDots;

    private Button nBtn;
    private Button bBtn;
    private int currentPage;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlide = findViewById(R.id.slide);
        mDots = findViewById(R.id.dots);

        nBtn = findViewById(R.id.nextBtn);
        bBtn = findViewById(R.id.backBtn);

        sliderAdapter = new SliderAdapter(this);

        mSlide.setAdapter(sliderAdapter);

        addDots(0);

        mDots.setHorizontalGravity(1);
        mDots.setVerticalGravity(1);

        mSlide.addOnPageChangeListener(viewListener);

        nBtn.setOnClickListener((View view) -> mSlide.setCurrentItem(currentPage+1));

        bBtn.setOnClickListener((View view) -> mSlide.setCurrentItem(currentPage-1));

    }

    public void addDots(int position){
        dots = new TextView[3];
        mDots.removeAllViews();

        for (int i =0; i< dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.swatch_2));

            mDots.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.swatch_4));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPage = position;

            if (position==0){
                bBtn.setEnabled(false);
                bBtn.setVisibility(View.INVISIBLE);
                bBtn.setText("");
                nBtn.setEnabled(true);
                nBtn.setText("Next");
            }
            else if (position == dots.length-1){
                bBtn.setEnabled(true);
                bBtn.setVisibility(View.VISIBLE);
                bBtn.setText("Back");
                nBtn.setEnabled(true);
                nBtn.setText("Finish");
//
//                mSlide.setBackgroundColor(getResources().getColor(R.color.swatch_1));
//                mDots.setBackgroundColor(getResources().getColor(R.color.swatch_1));
            }
            else{
                bBtn.setEnabled(true);
                bBtn.setVisibility(View.VISIBLE);
                bBtn.setText("Back");
                nBtn.setEnabled(true);
                nBtn.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}