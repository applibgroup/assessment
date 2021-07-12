package com.example.toplibrariesassignment;

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

import com.example.toplibrariesassignment.Model.ModelData;
import com.example.toplibrariesassignment.Model.VisitorComment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AdapterViewHolder> {
    Context context;

    private final ArrayList <ModelData> locationItemsList;

    public ItemAdapter(Context context, ArrayList<ModelData> locationItemsList) {
        this.context = context;
        this.locationItemsList = locationItemsList;

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_location_item, null);

        return new AdapterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, final int i) {
        final ModelData item = locationItemsList.get(i);

        String title = item.getName();
        String rating = String.valueOf(item.getRating());
        String reviews = String.valueOf(item.getReviews());
        String urlToImage = item.getImg_url();
        String description = item.getDescription();
        String details = item.getDetails();
        ArrayList<VisitorComment> visitorComment = item.getVisitor_comments();
        if (visitorComment == null)
        {
            Log.d("LOCAPI", "Visitor comments null");
        }
        final String url = item.getWiki_url();

        if(title!=null)
            holder.titleText.setText(title);
        // If the author name wasn't provided, the author item will not display on the news fragment
        holder.ratingText.setText(rating);
        holder.reviewsText.setText("Reviews: " + reviews);
        if(urlToImage!=null && isValidImgUrl(urlToImage))
            Picasso.get().load(urlToImage).into(holder.newsItemImageView);
        else holder.newsItemImageView.setVisibility(View.GONE);
        if (description != null && !description.equals("null")){
            holder.descriptionText.setText(description);
        }

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Method will create the activity that shows the news article in detail
                Intent intent  = new Intent(context,DetailsActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("img_url",urlToImage);
                intent.putExtra("details", details);
                intent.putExtra("comments", visitorComment);
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
        return locationItemsList.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView ratingText;
        TextView reviewsText;
        TextView descriptionText;
        ImageView newsItemImageView;
        Button detailsButton;
        AdapterViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.newsItemTitle);
            newsItemImageView = itemView.findViewById(R.id.newsItemImageView);
            ratingText = itemView.findViewById(R.id.newsItemRating);
            reviewsText = itemView.findViewById(R.id.newsItemReviews);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            descriptionText = itemView.findViewById(R.id.newsItemDesc);
        }

    }
}
