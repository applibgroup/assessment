package com.example.newsfeedassignment.Model;

public class ModelData {

    String title;
    String author;
    String date;
    String urlToImage;
    String url;
    String description;

    public ModelData(String title, String author, String date, String urlToImage, String url, String description) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.urlToImage = urlToImage;
        this.url = url;
        this.description = description;}

    public String getTitle() {return title; }
    public void setTitle(String title) {this.title = title; }
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
    public String getUrlToImage() {return urlToImage;}
    public void setUrlToImage(String urlToImage) {this.urlToImage = urlToImage;}
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getDescription() {return description; }
    public void setDescription(String description) {this.description = description;}

}
