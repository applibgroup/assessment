package com.example.newsfeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("articles")
    @Expose
    private final List<Article> articles;

    public News(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

}
