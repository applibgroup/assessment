package com.example.imageslider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private String[] imageurls;

    ViewPagerAdapter(Context context, String[] imageurls){
        this.context=context;
        this.imageurls=imageurls;
    }

    @Override
    public int getCount() {
        return imageurls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView= new ImageView(context);
        Picasso.get()
                .load(imageurls[position])
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
