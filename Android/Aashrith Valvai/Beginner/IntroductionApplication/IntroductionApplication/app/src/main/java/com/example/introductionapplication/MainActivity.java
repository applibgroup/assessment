package com.example.introductionapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private Button skip_button, back_button, next_button;
    private RelativeLayout background;
    private final int total_pages = 4;
    private int current_page;

    private final int[] background_colours = {
            R.color.background_page_0,
            R.color.background_page_1,
            R.color.background_page_2,
            R.color.background_page_3,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_pager = findViewById(R.id.view_pager);
        skip_button = findViewById(R.id.btn_skip);
        next_button = findViewById(R.id.btn_next);
        back_button = findViewById(R.id.btn_back);
        background = findViewById(R.id.background);

        current_page = 0;
        background.setBackgroundColor(getResources().getColor(background_colours[current_page]));
        back_button.setVisibility(View.GONE);

        PageAdapter page_adapter = new PageAdapter();
        view_pager.setAdapter(page_adapter);
        view_pager.addOnPageChangeListener(viewListener);

        skip_button.setOnClickListener(view -> finish());

        back_button.setOnClickListener(view -> {
            current_page = current_page - 1;
            view_pager.setCurrentItem(current_page);
        });

        next_button.setOnClickListener(view -> {
            current_page = current_page + 1;
            if (current_page < total_pages) {
                view_pager.setCurrentItem(current_page);
            }
            else {
                finish();
            }
        });
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            current_page = position;
            background.setBackgroundColor(getResources().getColor(background_colours[current_page]));
            next_button.setVisibility(View.VISIBLE);
            if (position == total_pages - 1) {
                next_button.setText(getString(R.string.gotit));
                skip_button.setVisibility(View.GONE);
                back_button.setVisibility(View.VISIBLE);
            }
            else if (position < 1) {
                back_button.setVisibility(View.GONE);
            }
            else {
                next_button.setText(getString(R.string.next));
                skip_button.setVisibility(View.VISIBLE);
                back_button.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class PageAdapter extends PagerAdapter {

        private final int[] active_dot_colours = {
                R.color.active_dot_page_0,
                R.color.active_dot_page_1,
                R.color.active_dot_page_2,
                R.color.active_dot_page_3,
        };

        private final int[] inactive_dot_colours = {
                R.color.inactive_dot_page_0,
                R.color.inactive_dot_page_1,
                R.color.inactive_dot_page_2,
                R.color.inactive_dot_page_3,
        };

        private final int[] icons = {
                R.drawable.food,
                R.drawable.movie,
                R.drawable.travel,
                R.drawable.discount,
        };

        private final int[] titles = {
                R.string.title_page_0,
                R.string.title_page_1,
                R.string.title_page_2,
                R.string.title_page_3,
        };

        private final int[] descriptions = {
                R.string.description_page_0,
                R.string.description_page_1,
                R.string.description_page_2,
                R.string.description_page_3,
        };

        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            LayoutInflater layout_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layout_inflater.inflate(R.layout.welcome_page, container, false);

//            RelativeLayout page = (RelativeLayout) findViewById(R.id.page);

            ImageView icon = view.findViewById(R.id.page_image);
            icon.setImageResource(icons[position]);

            TextView title = view.findViewById(R.id.page_title);
            title.setText(titles[position]);



            LinearLayout dots = view.findViewById(R.id.dots);
            dots.removeAllViews();
            for (int count = 0; count < total_pages; count++) {
                TextView dot = new TextView(dots.getContext());
                dot.setTextSize(35);
                dot.setText(Html.fromHtml("&#8226"));
                if (count == position) {
                    dot.setTextColor(getResources().getColor(active_dot_colours[position]));
                }
                else {
                    dot.setTextColor(getResources().getColor(inactive_dot_colours[position]));
                }
                dots.addView(dot);
            }


            TextView description = view.findViewById(R.id.page_description);
            description.setText(descriptions[position]);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return total_pages;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}