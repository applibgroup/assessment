package com.example.mynewsapp.parameter;

//public class Headlines {
//}
//package com.example.newsapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Headlines {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("articles")
    @Expose
    private List<Articles> articles;

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
