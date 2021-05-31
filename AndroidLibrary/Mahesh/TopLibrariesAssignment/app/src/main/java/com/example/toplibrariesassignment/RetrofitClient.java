package com.example.toplibrariesassignment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private LocationApi locationApi;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LocationApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        locationApi = retrofit.create(LocationApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public LocationApi getLocationApi() {
        return locationApi;
    }
}
