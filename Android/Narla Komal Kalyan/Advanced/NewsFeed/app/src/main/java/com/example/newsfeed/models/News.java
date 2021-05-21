package com.example.newsfeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private int totalResults;

    @SerializedName("articles")
    @Expose
    private List<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResults;
    }

    public void setTotalResult(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
