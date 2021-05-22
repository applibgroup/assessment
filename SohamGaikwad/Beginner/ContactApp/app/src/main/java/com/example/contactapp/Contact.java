package com.example.contactapp;

import androidx.annotation.DrawableRes;

public class Contact {
    private String name;
    private String number;
    private String email;
    private @DrawableRes int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
