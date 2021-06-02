package com.applibgroup.newsfeed;

import com.applibgroup.newsfeed.Models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiInterface {
    @GET("top-headlines")
    Call<ResponseModel> getTopHeadlines(@Query("country") String country,@Query("pageSize") int pageSize, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> getCategoryHeadlines(@Query("country") String country,@Query("category") String category,@Query("pageSize") int pageSize, @Query("apiKey") String apiKey);
}