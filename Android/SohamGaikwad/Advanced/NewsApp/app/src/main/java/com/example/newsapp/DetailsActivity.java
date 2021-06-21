package com.example.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        WebView editWeb = findViewById(R.id.web);
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        editWeb.loadUrl(url);

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("News");
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
