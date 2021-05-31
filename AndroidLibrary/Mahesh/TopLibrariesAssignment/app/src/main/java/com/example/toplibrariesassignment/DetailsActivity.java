package com.example.toplibrariesassignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toplibrariesassignment.Model.ModelData;
import com.example.toplibrariesassignment.Model.VisitorComment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String img_url = intent.getStringExtra("img_url");
        String details = intent.getStringExtra("details");
        ArrayList<VisitorComment> visitorComments = intent.getParcelableExtra("comments");

        TextView titleText = findViewById(R.id.detailsPageTitle);
        TextView detailsText = findViewById(R.id.detailsPageDetails);
        ImageView imageView = findViewById(R.id.detailsPageImageView);

        if (title != null)
            titleText.setText(title);
        if (details != null)
            detailsText.setText(details);
        if (img_url != null)
            Picasso.get().load(img_url).into(imageView);
        ActionBar bar = getSupportActionBar();

        if (bar != null){
            bar.setTitle("Detailed News");
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}