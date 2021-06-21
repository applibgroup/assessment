package com.example.pictoria.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("webformatURL")
    @Expose()
    private String webformatURL;

    public void setUrl(String url){ this.webformatURL = url; }

    public String getUrl(){return this.webformatURL;}
}
