package com.example.newsnow;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class  NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<NewsItem> news_list=new ArrayList<>();
    private Context mainActivity
            ;
    public NewsAdapter(Context mainActivity)
    {
        this.mainActivity=mainActivity;
    }
    public void getNewsList(List<NewsItem> l)
    {
        news_list=l;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull   MyViewHolder holder, int position) {
        NewsItem newsItem=news_list.get(position);
        Log.d("onBindViewHolder","heading: "+newsItem.heading);
        holder.headline.setText(newsItem.heading);
        Glide.with(holder.img.getRootView()).load(newsItem.img_url).into(holder.img);
        View.OnClickListener openURL =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                int colorInt = Color.parseColor("#BB86FC");
                builder.setToolbarColor(colorInt);
                CustomTabsIntent customTabsIntent = builder.build();
                Uri x=Uri.parse(newsItem.news_url);
                customTabsIntent.launchUrl(mainActivity, x);
            }
        };
        holder.headline.setOnClickListener(openURL);
        holder.img.setOnClickListener(openURL);
    }

    @Override
    public int getItemCount() {
        return news_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView headline;
        ImageView img;

        public MyViewHolder(@NonNull   View itemView) {
            super(itemView);
            headline =itemView.findViewById(R.id.xCategory);
            img=itemView.findViewById(R.id.xImageView);
        }
    }
}
