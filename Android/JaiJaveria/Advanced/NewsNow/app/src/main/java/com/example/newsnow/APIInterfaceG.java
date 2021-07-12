package com.example.newsnow;

import retrofit.Callback;
import retrofit.http.GET;

public interface APIInterfaceG {
    @GET("/v2/top-headlines?category=general&language=en&country=in&apiKey=b6bac42b86514a3d85237f7de4c2f2fa")

    // specify the sub url for our base url
    public void getNews(Callback<Pojo> callback);
}
