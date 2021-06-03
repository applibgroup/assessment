package com.example.newsapp;

public class ModelItems {

    String title, author, date, urlToImage, url;

    public ModelItems(String title, String author, String date, String urlToImage, String url) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.urlToImage = urlToImage;
        this.url = url; }

    public String getTitle() {return title; }

    public String getAuthor() {return author;}

    public String getDate() {return date;}

    public String getUrlToImage() {return urlToImage;}

    public String getUrl() {return url;}
}
