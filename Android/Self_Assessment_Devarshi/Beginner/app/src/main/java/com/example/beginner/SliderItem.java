package com.example.beginner;

import android.view.ViewDebug;

public class SliderItem {
    private int image;
    private String title;
    private String description;
    public SliderItem(int image, String title, String description)
    {
        this.image=image;
        this.description= description;
        this.title=title;
    }
    public int getImage()
    {
        return this.image;
    }
    public String getTitle()
    {
        return this.title;
    }
    public String getDescription()
    {
        return this.description;
    }
}
