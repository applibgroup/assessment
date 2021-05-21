package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsfeed.api.ApiClient;
import com.example.newsfeed.api.ApiInterface;
import com.example.newsfeed.models.Article;
import com.example.newsfeed.models.News;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "86bab5e48b1b45cfb2fa389fc33ed9e3";
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary_dark);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        reduceChoreographerSkippedFramesWarningThreshold();
        onLoadingSwipeRefresh();

    }

    public void LoadJson() {
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        language = language.toLowerCase();

        Call<News> call;
        call = apiInterface.getNews("apple", language, "publishedAt", API_KEY);
        // call = apiInterface.getNews("cnn", API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticles();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                }
                else {
                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    private void initListener(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, FeedDetails.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        LoadJson();
    }

    private void onLoadingSwipeRefresh(){
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do your function;
                LoadJson();
                handler.postDelayed(runnable, 30*1000);
            }
        }, 30*1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void reduceChoreographerSkippedFramesWarningThreshold() {
        if (BuildConfig.DEBUG) {
            Field field = null;
            try {
                field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
                field.setAccessible(true);
                field.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, 5);
            } catch (Throwable e) {
                Log.e(TAG, "failed to change choreographer's skipped frames threshold");
            }
        }
    }
}