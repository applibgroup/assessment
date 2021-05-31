package com.applibgroup.nearbyrestaurant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/"; //?location=-33.8670522,151.1957362&radius=1500&type=restaurant&key=AIzaSyDEX1pgBwvKSzwgjV3g4V1p-E6PEvUSn_Q
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}