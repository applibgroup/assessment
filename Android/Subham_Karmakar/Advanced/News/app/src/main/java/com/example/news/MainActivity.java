package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.adapter.RecyclerViewAdapter;
import com.example.news.model.Post;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnNewsPostClickListener
{
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(postList, MainActivity.this, this);


        NewsApiClient newsApiClient = new NewsApiClient("8987e832944046e2b543ec9baee0feb9");

        Toast.makeText(MainActivity.this,"Fetching...", Toast.LENGTH_SHORT).show();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("india")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        for (int i = 0; i <response.getArticles().size() ; i++)
                        {
                            postList.add(new Post(
                                    response.getArticles().get(i).getAuthor(),
                                    response.getArticles().get(i).getDescription(),
                                    response.getArticles().get(i).getPublishedAt(),
                                    response.getArticles().get(i).getTitle(),
                                    response.getArticles().get(i).getUrl(),
                                    response.getArticles().get(i).getUrlToImage()
                            ));

                            recyclerView.setAdapter(recyclerViewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("ABC", throwable.toString());
                    }
                }
        );



    }

    @Override
    public void onPostClick(int position)
    {
        Intent intent = new Intent(MainActivity.this, ShowNews.class);
        intent.putExtra("author", postList.get(position).getAuthor());
        intent.putExtra("description", postList.get(position).getDescription());
        intent.putExtra("publishedAt", postList.get(position).getPublishedAt());
        intent.putExtra("title", postList.get(position).getTitle());
        intent.putExtra("url", postList.get(position).getUrl());
        intent.putExtra("urlToImage", postList.get(position).getUrlToImage());
        startActivity(intent);
    }
}