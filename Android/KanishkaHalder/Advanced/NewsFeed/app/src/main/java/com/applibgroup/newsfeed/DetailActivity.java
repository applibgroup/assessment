package com.applibgroup.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applibgroup.newsfeed.Models.Article;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent().getSerializableExtra("ARTICLE") != null) {

            article = (Article) getIntent().getSerializableExtra("ARTICLE");

            ImageView imageView = findViewById(R.id.image_view_detail);
            TextView title = findViewById(R.id.text_view_title_detail);
            TextView content = findViewById(R.id.text_view_content_detail);
            TextView author = findViewById(R.id.text_view_author_detail);
            TextView dateTime = findViewById(R.id.text_view_date_time_detail);
            TextView source = findViewById(R.id.text_view_source_detail);
            TextView fullCoverage = findViewById(R.id.text_view_full_coverage);

            Glide.with(imageView.getContext())
                    .load(article.getUrlToImage())
                    .into(imageView);
            
            title.setText(article.getTitle());

            if(article.getContent()!=null){
                content.setText(article.getContent());
            }else if (article.getDescription() != null){
                content.setText(article.getDescription());
            }

            if(article.getAuthor() != null){
                author.setText(article.getAuthor());
            } else {
                author.setVisibility(View.GONE);
            }

            if(article.getSource() != null){
                source.setText(article.getSource().getName());
            } else {
                source.setVisibility(View.GONE);
            }

            if(article.getPublishedAt()!= null){
                try {
                    String date = getDateTime(article.getPublishedAt());
                    dateTime.setText(date);
                } catch (Exception e){
                    e.printStackTrace();
                    dateTime.setVisibility(View.GONE);
                }
            }

            if(article.getUrl() != null){
                fullCoverage.setText(article.getUrl());
                fullCoverage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                        intent.putExtra("URL",article.getUrl());
                        startActivity(intent);
                    }
                });
            }

        }
        else {
            Toast.makeText(this, "Error in fetching Article", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public String getDateTime(String worldStandartTimeString) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = inputFormat.parse(worldStandartTimeString);
        String formattedDateTime = outputFormat.format(date);
        return formattedDateTime;
    }
}
