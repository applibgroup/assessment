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

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.delivery,
            R.drawable.service,
            R.drawable.food_menu
    };

    public String[] slide_headings = {
            "Fastest Food\nDelivery",
            "Find best restaurant\nservice",
            "Browse all menu and\norder food"
    };

    public String[] slide_desc = {
            "Your order will be immediately collected\nand sent by our best and fastest food\ndelivery courier",
            "You can find best restaurant service,\ndelicious food, best professional chef\nservice",
            "Browse the menu and order food directly\nform the application, yups, you can find\nyour food easily"
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

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
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}
