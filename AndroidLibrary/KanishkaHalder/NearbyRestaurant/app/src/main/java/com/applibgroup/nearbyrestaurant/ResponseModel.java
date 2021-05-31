package com.applibgroup.nearbyrestaurant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ResponseModel {

    @SerializedName("status")
    private String status;
    @SerializedName("results")
    private List<Place> places = null;
    @SerializedName("next_page_token")
    private String nextToken=null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
