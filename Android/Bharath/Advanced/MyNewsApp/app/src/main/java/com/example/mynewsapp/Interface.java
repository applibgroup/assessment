//package com.example.mynewsapp;

//public interface Interface {
//}
package com.example.mynewsapp;

import com.example.mynewsapp.parameter.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Interface {
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}