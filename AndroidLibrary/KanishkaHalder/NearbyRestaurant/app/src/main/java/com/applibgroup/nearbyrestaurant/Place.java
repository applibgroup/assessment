package com.applibgroup.nearbyrestaurant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

class Place implements Serializable {

    @SerializedName("business_status")
    private String status;
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("name")
    private String name;
    @SerializedName("rating")
    private double rating;
    @SerializedName("vicinity")
    private String vicinity;
    @SerializedName("photos")
    private List<PhotoDetail> photoDetailsList ;
    @SerializedName("types")
    private List<String> tags;
    @SerializedName("user_ratings_total")
    private int numRatings;
    @SerializedName("opening_hours")
    private OpenHours openHours;
    @SerializedName("distance")
    private String distance;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public OpenHours getOpenHours() {
        return openHours;
    }

    public void setOpenHours(OpenHours openHours) {
        this.openHours = openHours;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public List<PhotoDetail> getPhotoDetailsList() {
        return photoDetailsList;
    }

    public void setPhotoDetailsList(List<PhotoDetail> photoDetailsList) {
        this.photoDetailsList = photoDetailsList;
    }

    public class Geometry implements Serializable{
        @SerializedName("location")
        public LocationModel location;

        public LocationModel getLocation() {
            return location;
        }

        public void setLocation(LocationModel location) {
            this.location = location;
        }
    }

    public class LocationModel implements Serializable{
        @SerializedName("lat")
        public double lat;

        @SerializedName("lng")
        public double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public class PhotoDetail implements Serializable{
        @SerializedName("height")
        private int height;
        @SerializedName("width")
        private int width;
        @SerializedName("photo_reference")
        private String photoRefference;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getPhotoRefference() {
            return photoRefference;
        }

        public void setPhotoRefference(String photoRefference) {
            this.photoRefference = photoRefference;
        }
    }

    public class OpenHours implements Serializable{
        @SerializedName("open_now")
        private Boolean openNow;

        public Boolean getOpenNow() {
            return openNow;
        }

        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }
    }

}
