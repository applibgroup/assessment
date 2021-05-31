package com.example.splashscreen;

import android.content.Context;
import android.util.Log;
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
    LayoutInflater li;
    public SliderAdapter(Context context)
    {
        this.context=context;
    }
    //Arrays
//    images taken from pixabay.com
    public int[] slide_images= {

            R.drawable.virus,
            R.drawable.handwash,
            R.drawable.social_dis
    };
    public static String[] slide_headings={
        "Pandemic",
        "Using Handwash",
        "Social Distancing"};
    public static String[] slide_text={
            "The Covid 19 Pandemic has come back in the country once again. As we all are working from home, some reminders to win over the second wave.",
            "Keep sanitizing and cleaning your hands regularly, especially before meals and after coming back home from outside. Doing so kills the virus and protects you and your family!",
            "Keep practising social distancing, not only when in public places but also inside your home. By doing these small things we can help in breaking the chain of infections."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object object) {
        return view==(RelativeLayout) object;
    }


    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        String LOG_TAG=  MainActivity.class.getSimpleName();
        Log.d(LOG_TAG,"inside slider adapter instantiate item");
//        return super.instantiateItem(container, position);
        li = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =li.inflate(R.layout.slidelayout, container, false);
        ImageView iv=(ImageView) view.findViewById(R.id.slide_image);
        TextView tvh= (TextView) view.findViewById(R.id.slide_heading);
        TextView tvt=(TextView) view.findViewById(R.id.slide_text);
        iv.setImageResource(slide_images[position]);
        tvh.setText(slide_headings[position]);
        tvt.setText(slide_text[position]);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
