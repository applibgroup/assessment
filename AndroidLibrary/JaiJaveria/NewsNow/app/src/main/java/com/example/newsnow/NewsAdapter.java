package com.example.newsnow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class  NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<NewsItem> news_list = new ArrayList<>();
    private Context mainActivity;

    public NewsAdapter(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getNewsList(List<NewsItem> l) {
        news_list = l;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
//        firestore=FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsItem newsItem = news_list.get(position);
//        Log.d("onBindViewHolder", "heading: " + newsItem.heading);
        holder.headline.setText(newsItem.heading);
        Log.d("NewsAdapter", "img width"+holder.img.getWidth());
        holder.img.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    // Wait until layout to call Picasso
                    @Override
                    public void onGlobalLayout() {
                        // Ensure we call this only once
                        holder.img.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        Picasso.get().load(newsItem.img_url).resize(holder.img.getMeasuredWidth(),0).into(holder.img);
                }});
//        Glide.with(holder.img.getRootView()).load(newsItem.img_url).into(holder.img);
        View.OnClickListener openURL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                int colorInt = Color.parseColor("#BB86FC");
                builder.setToolbarColor(colorInt);
                CustomTabsIntent customTabsIntent = builder.build();
                Uri x = Uri.parse(newsItem.news_url);
                customTabsIntent.launchUrl(mainActivity, x);
            }
        };
        View.OnClickListener setQRCode = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.share.getText().equals("Share"))
                {
//                    holder.qrCode.hei
                    try {
                        generateQRCode_general(newsItem.news_url, holder.qrCode);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    holder.qrCode.setVisibility(View.VISIBLE);
                    holder.share.setText("Close the QRCode");
                }
                else
                {
//                    holder.qrCode.setI;
                    holder.qrCode.setVisibility(View.GONE);
                    holder.share.setText("Share");

                }

            }
        };
        holder.headline.setOnClickListener(openURL);
        holder.img.setOnClickListener(openURL);
        holder.share.setOnClickListener(setQRCode);
    }

    @Override
    public int getItemCount() {
        return news_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView headline;
        TextView share;
        ImageView img;
        ImageView qrCode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.xCategory);
            img = itemView.findViewById(R.id.xImageView);
            qrCode=itemView.findViewById(R.id.xQRView);
            share=itemView.findViewById(R.id.xShare);
        }
    }

    private void generateQRCode_general(String data, ImageView img) throws WriterException {
        Log.d("generateQRCode", "input String "+data);
        com.google.zxing.Writer writer = new QRCodeWriter();
        int squareSize=600;
        BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE, squareSize, squareSize);
        Bitmap ImageBitmap = Bitmap.createBitmap(squareSize, squareSize, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < squareSize; i++) {//width
            for (int j = 0; j < squareSize; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
            }
        }

        if (ImageBitmap != null) {
            img.setImageBitmap(ImageBitmap);
        } else {
            Log.e("generateQRCode", "Null bitmap");

        }
    }
}