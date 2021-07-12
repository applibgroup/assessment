package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.newsfeed.models.Article;
import com.example.newsfeed.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    ProgressBar progressBar;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<Article> articles;
    String API="599aac330a114ce8977ad8b6ccc50a20";
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=599aac330a114ce8977ad8b6ccc50a20


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);



        articles=new ArrayList<>();
        String country=getCountry();
        retriveJson(country,API);



    }
    public void retriveJson(String country,String apiKey){
        Call<News> call=ApiClient.getInstance().getApi().getNews(country,apiKey);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                progressBar.setVisibility(View.INVISIBLE);
                articles.clear();
                articles=response.body().getArticles();
                myAdapter=new MyAdapter(getApplicationContext(),articles);
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    public String getCountry(){
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();

    }

/*    private void getJson() {

        String url="http://newsapi.org/v2/everything?q=keyword&apiKey=599aac330a114ce8977ad8b6ccc50a20";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Got"+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Not got"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage()+"next"+ error.networkResponse.statusCode+
                        ">>" + error.networkResponse.data
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
            }
        });
        Log.d(TAG, "getJson: before reqque");
        
        requestQueue.add(stringRequest);

    }*/
}