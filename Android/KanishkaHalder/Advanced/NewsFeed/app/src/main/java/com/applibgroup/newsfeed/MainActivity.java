package com.applibgroup.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.applibgroup.newsfeed.Models.Article;
import com.applibgroup.newsfeed.Models.ResponseModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ViewAdapter.OnViewClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String API_KEY = BuildConfig.NEWS_API_KEY;
    private static List<Article> articleList = new ArrayList<>();

    private ViewAdapter adapter;

    private DrawerLayout drawer;

    private String INDIA_ISO_CODE = "in";
    private String CATEGORY_BUSINESS = "business";
    private String CATEGORY_SPORTS = "sports";
    private String CATEGORY_TECHNOLOGY = "technology";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fetchTopHeadalines("in");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_news_feed);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };
        linearLayoutManager.isAutoMeasureEnabled();
        adapter = new ViewAdapter(articleList,this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void fetchTopHeadalines(String countryCode){
        final ApiInterface apiService = Api.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getTopHeadlines(countryCode,100,API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok")){
                    if(response.body().getTotalResults()>0){
                        articleList = response.body().getArticles();
                        adapter.updateList(articleList);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCategoryHeadalines(String category){
        final ApiInterface apiService = Api.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getCategoryHeadlines(INDIA_ISO_CODE,category,100,API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok")){
                    if(response.body().getTotalResults()>0){
                        articleList = response.body().getArticles();
                        adapter.updateList(articleList);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("ARTICLE",articleList.get(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_headline:
                fetchTopHeadalines(INDIA_ISO_CODE);
                Log.d("NAVIGATION SELECTED", "onNavigationItemSelected: HEADLINE");
                break;
            case R.id.nav_business:
                fetchCategoryHeadalines(CATEGORY_BUSINESS);
                Log.d("NAVIGATION SELECTED", "onNavigationItemSelected: Business");
                break;
            case R.id.nav_Sport:
                fetchCategoryHeadalines(CATEGORY_SPORTS);
                Log.d("NAVIGATION SELECTED", "onNavigationItemSelected: Sports");
                break;
            case R.id.nav_tech:
                fetchCategoryHeadalines(CATEGORY_TECHNOLOGY);
                Log.d("NAVIGATION SELECTED", "onNavigationItemSelected: Tech");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
