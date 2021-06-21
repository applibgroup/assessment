package com.example.pictoria.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFromAPI {
    @SerializedName("hits")
    @Expose()
    private List<Item> hits;

    public void setHits(List<Item> hits) { this.hits = hits;}

    public List<Item> getHits(){return this.hits;}
}
