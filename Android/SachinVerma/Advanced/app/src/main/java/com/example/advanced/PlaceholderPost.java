package com.example.advanced;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceholderPost {
    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("urltoImage")
    @Expose
    private String urltoImage;

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @SerializedName("content")
    @Expose
    private String content;

    public void setSource() {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public void setAuthor() {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription() {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl() {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrltoImage() {
        this.urltoImage = urltoImage;
    }

    public String getUrltoImage() {
        return urltoImage;
    }

    public void setPublishedAt() {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setContent() {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
