package com.example.beginner;

import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{


    public List<SliderItem> mExampleList;
    private OnItemClickListener mListener;
    private ViewPager2 viewPager2;


    public interface OnItemClickListener{
        void onNextClick(int position);
        void onSkipClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) { mListener=listener;}


    public static class SliderViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public Button SkipBtn,NextBtn;

        public SliderViewHolder(@NonNull View itemView, final OnItemClickListener listener) {

            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView1) ;
            mTextView1=itemView.findViewById(R.id.item_title);
            mTextView2=itemView.findViewById(R.id.item_description);
             SkipBtn= (Button) itemView.findViewById( R.id.skip );
            NextBtn = (Button) itemView.findViewById( R.id.next );
            SkipBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onSkipClick(position);
                        }
                    }
                }
            });
            NextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onNextClick(position);
                        }
                    }

                }
            });
        }
    }


    public SliderAdapter(List<SliderItem> exampleList,ViewPager2 viewPager2){
        mExampleList=exampleList;
        this.viewPager2=viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.splash_screen_container,parent,false);
        SliderViewHolder tah=new SliderViewHolder(v,mListener);
        return tah;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem currItem = mExampleList.get(position);
        holder.mTextView1.setText(currItem.getTitle());
        holder.mTextView2.setText(currItem.getDescription());
        holder.mImageView.setImageResource(currItem.getImage());
    }


    @Override
    public int getItemCount() {

        return mExampleList.size();
    }

}

