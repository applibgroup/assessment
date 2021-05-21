package com.example.contactsdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView simpleList;
    String countryList[] = {"Shikhar Dhawan", "Rohit Sharma", "Virat Kohli", "Suryakumar Yadav", "Rishabh Pant", "Hardik Pandya", "Ravinder Jadeja", "Rahul Chahar", "Jasprit Bumrah", "Bhuvaneshwar Kumar"};
    int flags[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showImage= new Intent(getApplicationContext(),DetailActivity.class);
                showImage.putExtra("Item_Index",position);
                startActivity(showImage);
            }
        });
    }
}