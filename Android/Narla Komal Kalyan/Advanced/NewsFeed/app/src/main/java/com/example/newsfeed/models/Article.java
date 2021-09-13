package com.example.newsfeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("source")
    @Expose
    private final Source source;

    @SerializedName("author")
    @Expose
    private final String author;

    @SerializedName("title")
    @Expose
    private final String title;

    @SerializedName("description")
    @Expose
    private final String description;

    @SerializedName("url")
    @Expose
    private final String url;

    @SerializedName("urlToImage")
    @Expose
    private final String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    private final String publishedAt;

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

}
