package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowNews extends AppCompatActivity
{
    TextView author;
    TextView description;
    TextView publishedAt;
    TextView title;
    TextView url;
    TextView image;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        author = findViewById(R.id.author_specific);
        description = findViewById(R.id.description_specific);
        publishedAt = findViewById(R.id.published_specific);
        title = findViewById(R.id.title_specific);
        url = findViewById(R.id.url_specific);
        image = findViewById(R.id.image_specific);

        Intent intent = getIntent();

        author.setText(intent.getStringExtra("author"));
        description.setText(intent.getStringExtra("description"));
        publishedAt.setText(intent.getStringExtra("publishedAt"));
        title.setText(intent.getStringExtra("title"));
        url.setText(intent.getStringExtra("url"));
        image.setText(intent.getStringExtra("urlToImage"));
    }
}