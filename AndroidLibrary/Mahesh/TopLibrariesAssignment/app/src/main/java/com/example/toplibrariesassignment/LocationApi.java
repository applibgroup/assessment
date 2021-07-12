package com.example.toplibrariesassignment;

import com.example.toplibrariesassignment.Model.LocationApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocationApi {
    String BASE_URL = "https://elaitenstile.github.io/touristapi-test/";
    @GET("{LocationName}.json")
    Call<LocationApiResponse> getLocationDetailsAt(@Path("LocationName") String cityName);
}
