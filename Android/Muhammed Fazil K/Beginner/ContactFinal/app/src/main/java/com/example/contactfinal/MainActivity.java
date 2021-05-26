package com.example.contactfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<String> numbers=new ArrayList<>();
    private ArrayList<Integer> images=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        initImages();

    }
    private void initImages(){
        Log.d(TAG, "initImages: started");
        names.add("Andrew");
        images.add(R.drawable.download);
        numbers.add("833464964");

        names.add("Baby");
        images.add(R.drawable.person5);
        numbers.add("9065636364");

        names.add("Celena");
        images.add(R.drawable.download2);
        numbers.add("94534534");

        names.add("David");
        images.add(R.drawable.download3);
        numbers.add("44849862");

        names.add("Fazil");
        images.add(R.drawable.download);
        numbers.add("906464964");

        initRecyclerView();

    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        RecycleViewAdapter adapter=new RecycleViewAdapter(this,names,numbers,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}