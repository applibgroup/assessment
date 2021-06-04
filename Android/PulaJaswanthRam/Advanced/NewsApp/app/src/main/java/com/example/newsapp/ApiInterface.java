package com.example.newsapp;

import com.example.newsapp.data.Headline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headline> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headline> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

}
