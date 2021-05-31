package com.example.newsfeedassignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        WebView editWeb = findViewById(R.id.web);

        // To override url redirect
        editWeb.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        editWeb.loadUrl(url);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Detailed News");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}