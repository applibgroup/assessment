package com.example.newsfeed;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="https://newsapi.org/v2/";
    public static Retrofit retrofit;
    public static ApiClient apiClient;
    private static final String TAG = "ApiClient";

    private ApiClient(){
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static synchronized ApiClient getInstance(){
        Log.d(TAG, "getInstance: "+apiClient+"\n");
        if(apiClient==null){
            apiClient=new ApiClient();
        }

        return apiClient;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);

    }

}
