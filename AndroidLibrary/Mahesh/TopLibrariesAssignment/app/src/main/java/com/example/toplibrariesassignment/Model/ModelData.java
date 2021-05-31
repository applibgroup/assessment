package com.example.toplibrariesassignment.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelData {
    private final String name;
    private final String description;
    private final float rating;
    private final int reviews;
    @SerializedName("visitor_comments")
    public ArrayList<VisitorComment> visitor_comments;
    private final String details;
    private final String wiki_url;
    private final String img_url;

    public ModelData(String name, String description, float rating, int reviews, ArrayList<VisitorComment> visitor_comments, String details, String wiki_url, String img_url) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.reviews = reviews;
        this.visitor_comments = visitor_comments;
        this.details = details;
        this.wiki_url = wiki_url;
        this.img_url = img_url;
    }

    public ArrayList<VisitorComment> getVisitor_comments() {
        return visitor_comments;
    }

    public int getReviews() {
        return reviews;
    }

    public String getDescription() {
        return description;
    }

    public String getDetails() {
        return details;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getName() {
        return name;
    }

    public String getWiki_url() {
        return wiki_url;
    }

    public float getRating() {
        return rating;
    }

}
