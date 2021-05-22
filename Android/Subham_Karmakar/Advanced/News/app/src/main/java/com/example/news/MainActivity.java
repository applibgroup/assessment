package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private TextView news;
    private String headlines = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        news = findViewById(R.id.news);
        NewsApiClient newsApiClient = new NewsApiClient("8987e832944046e2b543ec9baee0feb9");


        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("india")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (int i = 0; i <response.getArticles().size() ; i++)
                        {
                            headlines = response.getArticles().get(i).getTitle() + "\n";
//                            headlines = response.getArticles().get(i).getDescription() + "\n\n";
//                            news.append(headlines);
                        }
//                        headlines = headlines + response.getArticles().get(0).getTitle() + "\n";
                        Log.d("ABC",response.getArticles().get(0).getTitle());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("ABC", throwable.toString());
                    }
                }
        );

    }
}