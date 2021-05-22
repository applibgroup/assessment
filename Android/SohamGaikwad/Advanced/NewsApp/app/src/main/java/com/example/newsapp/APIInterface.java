package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> getCountryNews(@Query("country") String country, @Query("apiKey") String apiKey);

}
