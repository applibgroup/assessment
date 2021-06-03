package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("top-headlines")
    Call<ResponseModel> getCategoryNews(@Query("category") String category, @Query("language") String language, @Query("apiKey") String apiKey);

}
