package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Array to store the text for each images
    private final String[] image_titles = {
            "Image1",
            "Image2",
            "Image3",
            "Image4",
            "Image5",
    };

    //Array to store the URL's of each image
    private final String[] image_urls = {
            "https://th.bing.com/th/id/OIP.ugi6ka2-mACRREYtCh7KUAHaEK",
            "https://th.bing.com/th/id/OIP.eLXqZSQogWkIatAKPpjAEQHaEo",
            "https://th.bing.com/th/id/OIP.uzasBNwxum5G7YTfZZAFEQHaEK",
            "https://th.bing.com/th/id/OIP.vzUhlFJFR5akQnwy8tWSvAHaF7",
            "https://th.bing.com/th/id/OIP.r3R99P4WVWFdma8uTf6tXAHaEo",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        Log.d("MainActivity", "CALLING ADAPTER CLASS");
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        Log.d("MainActivity", "ADAPTER CLASS RETURNED");
        recyclerView.setAdapter(adapter);
    }

    //Function to build array of images containing image's title and it's URL
    private ArrayList<CreateList> prepareData() {
        ArrayList<CreateList> Image = new ArrayList<>();
        for(int i = 0; i < image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_url(image_urls[i]);
            Image.add(createList);
        }
        return Image;
    }
}