package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ShowNews extends AppCompatActivity
{
    TextView author;
    TextView description;
    TextView published;
    TextView title;
    TextView url;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        author = findViewById(R.id.author);
        description = findViewById(R.id.description);
        published = findViewById(R.id.published);
        title = findViewById(R.id.title);
        url = findViewById(R.id.url);
        image = findViewById(R.id.image);

        Intent intent = getIntent();

        author.append(intent.getStringExtra("author"));
        description.setText(intent.getStringExtra("description"));
        published.append(intent.getStringExtra("publishedAt"));
        title.setText(intent.getStringExtra("title"));
        url.append(intent.getStringExtra("url"));
        Picasso.get().load(intent.getStringExtra("urlToImage")).into(image);
    }
}
