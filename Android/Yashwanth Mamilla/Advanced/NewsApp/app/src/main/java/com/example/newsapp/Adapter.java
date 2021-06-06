package com.example.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.parameter.Articles;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles> articles;
    Activity currAcc;
    public Adapter(Context context,List<Articles> articles,Activity currAcc){
        this.context=context;
        this.articles=articles;
        this.currAcc=currAcc;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        final Articles art = articles.get(position);
        String url=art.getUrl();
        holder.newsTitle.setText(art.getTitle());
        holder.newsDate.setText(art.getPublishedAt());
        String imageUrl=art.getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewsDetails.class);
                intent.putExtra("url",art.getUrl());
                context.startActivity(intent);
                //currAcc.overridePendingTransition(R.anim.slide_in_right,
                  //      R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDate;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            newsDate=itemView.findViewById(R.id.newsDate);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            imageView=itemView.findViewById(R.id.image);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
    public String getCountry(){
        Locale locale= Locale.getDefault();
        String country= locale.getCountry();
        return country.toLowerCase();
    }
}
