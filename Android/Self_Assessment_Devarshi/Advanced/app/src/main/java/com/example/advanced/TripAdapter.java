package com.example.advanced;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.advanced.models.Article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripAdapterHolder> {
    public List<Article> mExampleList;
    private OnItemClickListener mListener;
    public Context context;

    public interface OnItemClickListener{
        void onDetailsClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) { mListener=listener;}
    public static class TripAdapterHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public LinearLayout DetailBtn,DeleteBtn;

        public TripAdapterHolder(@NonNull View itemView, final OnItemClickListener listener) {

            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView1) ;
            mTextView1=itemView.findViewById(R.id.item_title);
            mTextView2=itemView.findViewById(R.id.item_description);
             DetailBtn =  itemView.findViewById( R.id.ll1 );
            DetailBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!= RecyclerView.NO_POSITION)
                        {
                            listener.onDetailsClick(position);
                        }
                    }
                }
            });

        }
    }
        public TripAdapter(List<Article> exampleList){
            mExampleList=exampleList;

        }

    @NonNull
    @Override
    public TripAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.map_list_item,parent,false);
        TripAdapterHolder tah=new TripAdapterHolder(v,mListener);
        return tah;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TripAdapterHolder holder, int position) {
        Article currItem = mExampleList.get(position);
      //  Glide.with(context).load(currItem.getUrlToImage()).into(holder.mImageView);

        holder.mTextView1.setText(currItem.getAuthor()+" | "+currItem.getPublishedAt());
        holder.mTextView2.setText(currItem.getTitle());

    }


    @Override
    public int getItemCount() {

        return mExampleList.size();
    }



}
