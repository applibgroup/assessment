package com.example.splashscreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    ViewPager viewPager;
    LinearLayout linearLayout;
    TextView[] textViewArray;
    int[] layouts;
    Button nextBtn;
    Button skipBtn;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = findViewById(R.id.viewpager);
        linearLayout = findViewById(R.id.linear_layout);
        nextBtn = findViewById(R.id.btn_next);
        skipBtn = findViewById(R.id.btn_skip);


        skipBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage < layouts.length)
                {
//                    currentPage++;
                    viewPager.setCurrentItem(currentPage);
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                    finish();
                }
            }
        });

        layouts = new int[] {R.layout.slide_1, R.layout.slide_2, R.layout.slide_3};
        mainAdapter = new MainAdapter(layouts, MainActivity.this);
        viewPager.setAdapter(mainAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if(position==layouts.length-1)
                {
                    nextBtn.setText("START");
                    skipBtn.setVisibility(View.GONE);
                }
                else
                {
                    nextBtn.setText("NEXT");
                    skipBtn.setVisibility(View.VISIBLE);
                }
                setDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        setDots(0);

    }

    private void setDots(int pageNo)
    {
        linearLayout.removeAllViews();
        textViewArray = new TextView[layouts.length];

        for (int i = 0; i < textViewArray.length; i++)
        {
            textViewArray[i] = new TextView(this);
            textViewArray[i].setText(Html.fromHtml("&#8226"));
            textViewArray[i].setTextColor(Color.parseColor("#FFFFC107"));
            linearLayout.addView(textViewArray[i]);
            textViewArray[i].setTextSize(30);
        }

        if(textViewArray.length > 0)
        {
            textViewArray[pageNo].setTextColor(Color.parseColor("#011aa7"));
        }
    }


}