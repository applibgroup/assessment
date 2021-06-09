package com.applibgroup.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applibgroup.newsfeed.Models.Article;
import com.applibgroup.newsfeed.Models.SourceModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private List<Article> articleList;
    private OnViewClickListener clickListener;

    public ViewAdapter(List<Article> dataList, OnViewClickListener onViewClickListener ){
        this.articleList = dataList;
        this.clickListener = onViewClickListener;
    }

    public void updateList(List<Article> list){
        this.articleList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_item_layout,parent,false);
        return new ViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);

        Glide.with(holder.imageView.getContext())
                .load(article.getUrlToImage())
                .into(holder.imageView);

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        if (article.getSource().getName() != null) {
            holder.source.setText(article.getSource().getName());
        } else {
            holder.source.setVisibility(View.GONE);
        }

        if (article.getPublishedAt() != null) {
            try {
                String dateTime = getDateTime(article.getPublishedAt());
                holder.time.setText(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
                holder.time.setVisibility(View.GONE);
            }

        } else {
            holder.time.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title;
        TextView description;
        TextView time;
        TextView source;

        OnViewClickListener viewClickListener;

        public ViewHolder(@NonNull View itemView, OnViewClickListener onViewClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_news_item);
            title = itemView.findViewById(R.id.text_view_title_news_feed);
            description = itemView.findViewById(R.id.text_view_desc_news_feed);
            time = itemView.findViewById(R.id.text_view_date_time);
            source = itemView.findViewById(R.id.text_view_source);
            this.viewClickListener = onViewClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            viewClickListener.onListItemClick(getAdapterPosition());
        }
    }
    public interface OnViewClickListener {
        void onListItemClick(int position);
    }

    public String getDateTime(String worldStandartTimeString) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = inputFormat.parse(worldStandartTimeString);
        String formattedDateTime = outputFormat.format(date);
        return formattedDateTime;
    }
}
