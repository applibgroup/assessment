package com.example.newsfeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("name")
    @Expose
    private final String name;

    public Source(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
