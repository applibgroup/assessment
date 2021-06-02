package com.applibgroup.nearbyrestaurant;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("nearbysearch/json")
    Call<ResponseModel> getNearbyPlaces(@Query("location") String location,@Query("radius") String radius,@Query("type") String type, @Query("key") String apiKey);

}