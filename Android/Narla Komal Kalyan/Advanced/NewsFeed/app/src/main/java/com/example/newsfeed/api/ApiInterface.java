package com.example.newsfeed.api;

import com.example.newsfeed.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<News> getNews(

            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );

//    @GET("top-headlines")
//    Call<News> getNews(
//
//            @Query("sources") String sources,
//            @Query("apiKey") String apiKey
//
//    );

}
