package com.example.pictoria;

import com.example.pictoria.Model.ResponseFromAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api")
    Call<ResponseFromAPI> getResponse(
        @Query("key")
            String key,
        @Query("q")
            String q
    );
}
