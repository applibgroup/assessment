package com.example.newsfeedassignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeedassignment.Model.ModelData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AdapterViewHolder> {
    Context context;

    private final ArrayList <ModelData> newsItemList;

    public ItemAdapter(Context context, ArrayList<ModelData> newsItemList) {
        this.context = context;
        this.newsItemList = newsItemList;

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_news_item, null);

        return new AdapterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, final int i) {
        final ModelData item = newsItemList.get(i);

        String title = item.getTitle();
        String author = item.getAuthor();
        String date = item.getDate();
        String urlToImage = item.getUrlToImage();
        String description = item.getDescription();
        final String url = item.getUrl();

        if(title!=null)
            holder.titleText.setText(title);
        // If the author name wasn't provided, the author item will not display on the news fragment
        if(author!=null && !author.equals("null"))
            holder.authorText.setText("Author  "+author);
        if(date!=null)
            holder.dateText.setText("published at: "+date);
        if(urlToImage!=null && isValidImgUrl(urlToImage))
        {Log.d("ITEMADAPTER", "Attempting to load img url: " + urlToImage);
            Picasso.get().load(urlToImage).into(holder.newsItemImageView);}
        else holder.newsItemImageView.setVisibility(View.GONE);
        if (description != null && !description.equals("null")){
            holder.descriptionText.setText(description);
        }

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Method will create the activity that shows the news article in detail
                Intent intent  = new Intent(context,DetailsActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });

    }

    private boolean isValidImgUrl(String urlToImage) {
        boolean outBool = true;
        if (urlToImage.isEmpty() || urlToImage.equals("null"))
            outBool = false;
        else{
            if (!urlToImage.startsWith("http"))
                outBool = false;
        }
        return outBool;
    }


    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView authorText;
        TextView dateText;
        TextView descriptionText;
        ImageView newsItemImageView;
        Button detailsButton;
        AdapterViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.newsItemTitle);
            newsItemImageView = itemView.findViewById(R.id.newsItemImageView);
            authorText = itemView.findViewById(R.id.newsItemAuthor);
            dateText = itemView.findViewById(R.id.newsItemDate);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            descriptionText = itemView.findViewById(R.id.newsItemDesc);
        }

    }
}
