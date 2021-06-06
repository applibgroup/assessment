package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsapp.parameter.Articles;
import com.example.newsapp.parameter.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    String API_KEY = "f7ad332b275f448b8385816cb68303e3";
    ImageView refresh;
    List<Articles> articles=new ArrayList<>();
    Activity myactivity= (Activity)this;
    // API KEY f7ad332b275f448b8385816cb68303e3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        refresh=findViewById(R.id.imageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String country = getCountry();
        fetchJSON(country,API_KEY);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJSON(country,API_KEY);
            }
        });
    }

    private void fetchJSON(String country, String api_key) {
        Call<Headlines> call=Client.getInstance().getApi().getHeadlines(country,api_key);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {
                    articles.clear();
                    articles=response.body().getArticles();
                    adapter=new Adapter(MainActivity.this,articles,myactivity);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getCountry(){
        Locale locale= Locale.getDefault();
        String country= locale.getCountry();
        return country.toLowerCase();
    }
}