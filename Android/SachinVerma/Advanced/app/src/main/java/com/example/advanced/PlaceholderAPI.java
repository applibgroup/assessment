package com.example.advanced;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.advanced.PlaceholderPost;

public interface PlaceholderAPI {
    @GET("top-headlines")
    Call<News> getPosts(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
