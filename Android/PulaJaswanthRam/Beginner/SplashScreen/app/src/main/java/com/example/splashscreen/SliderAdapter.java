package com.example.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(@NonNull Context context){
        this.context = context;
    }

    private final int[] slide_images = {
            R.drawable.delivery,
            R.drawable.service,
            R.drawable.food_menu
    };

    private final int[] slide_headings = {
            R.string.slide_headings_1,
            R.string.slide_headings_2,
            R.string.slide_headings_3
    };

    private final int[] slide_desc = {
            R.string.slide_desc_1,
            R.string.slide_desc_2,
            R.string.slide_desc_3
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container,@NonNull int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView);
        TextView slideHead = (TextView) view.findViewById(R.id.textView);
        TextView slideDesc = (TextView) view.findViewById(R.id.textView2);

        slideImageView.setImageResource(slide_images[position]);
        slideHead.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, @NonNull int position, @NonNull Object object){
        container.removeView((RelativeLayout)object);
    }
}
