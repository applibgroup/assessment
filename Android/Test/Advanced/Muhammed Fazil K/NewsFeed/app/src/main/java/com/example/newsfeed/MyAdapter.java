package com.example.newsfeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.models.Article;
import com.example.newsfeed.models.News;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<Article> articles;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article article=articles.get(position);
        holder.teTitle.setText(article.getTitle());
        holder.teDesc.setText(article.getDescription());
        holder.teDate.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage()).into(holder.imImage);


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView teTitle,teDesc,teDate;
        ImageView imImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teTitle=itemView.findViewById(R.id.newstitle);
            teDesc=itemView.findViewById(R.id.newsdesc);
            teDate=itemView.findViewById(R.id.newstime);
            imImage=itemView.findViewById(R.id.newsimage);
        }
    }


}
