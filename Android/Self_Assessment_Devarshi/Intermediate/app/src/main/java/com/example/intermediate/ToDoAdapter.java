package com.example.intermediate;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TripAdapterHolder> {
    public ArrayList<ToDo> mExampleList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onDetailsClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) { mListener=listener;}
    public static class TripAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public LinearLayout DetailBtn;
        public TripAdapterHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView1) ;
            mTextView1=itemView.findViewById(R.id.item_title);
            mTextView2=itemView.findViewById(R.id.item_description);
             DetailBtn =  itemView.findViewById( R.id.ll1 );
            DetailBtn.setOnClickListener(v -> {
                if(listener!=null)
                {
                    int position=getAdapterPosition();

                    if(position!= RecyclerView.NO_POSITION)
                    {
                        listener.onDetailsClick(position);
                    }
                }
            });
        }
    }
        public ToDoAdapter(ArrayList<ToDo> exampleList){
            mExampleList=exampleList;

        }
    @NonNull
    @Override
    public TripAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.map_list_item,parent,false);
        return new TripAdapterHolder(v,mListener);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TripAdapterHolder holder, int position) {
        ToDo currItem = mExampleList.get(position);
        switch (currItem.getType()) {
            case "Travel":
                holder.mImageView.setImageResource(R.drawable.travel);
                break;
            case "Party":
                holder.mImageView.setImageResource(R.drawable.party);
                break;
            case "Shopping":
                holder.mImageView.setImageResource(R.drawable.shopping);
                break;
            case "Gym":
                holder.mImageView.setImageResource(R.drawable.gym);
                break;
            default:
                holder.mImageView.setImageResource(R.drawable.others);
                break;
        }
        holder.mTextView1.setText(currItem.getTask());
        holder.mTextView2.setText(currItem.getDatetime());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
