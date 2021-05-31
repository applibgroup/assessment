package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.model.Post;
import com.kwabenaberko.newsapilib.NewsApiClient;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private OnNewsPostClickListener newsPostClickListener;
    private ArrayList<Post> postsList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Post> postsList, Context context, OnNewsPostClickListener onNewsPostClickListener)
    {
        this.postsList = postsList;
        this.context = context;
        this.newsPostClickListener = onNewsPostClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card,parent,false);
        return new ViewHolder(view, newsPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position)
    {
        Post post = postsList.get(position);

        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
    }

    @Override
    public int getItemCount()
    {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        OnNewsPostClickListener onNewsPostClickListener;

        public TextView title;
        public TextView description;

        public ViewHolder(@NonNull View itemView, OnNewsPostClickListener onNewsPostClickListener)
        {
            super(itemView);

            title = itemView.findViewById(R.id.post_title);
            description = itemView.findViewById(R.id.post_description);
            this.onNewsPostClickListener = onNewsPostClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onNewsPostClickListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnNewsPostClickListener
    {
        void onPostClick(int position);
    }
}
