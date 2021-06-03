package com.example.contactapp;

import androidx.annotation.DrawableRes;

public class Contact {
    private String name;
    private final String number;
    private final String email;
    private @DrawableRes int image;

    public int getImage() {
        return image;
    }

    public Contact(String name, String number, String email, int image) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

}
