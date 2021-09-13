package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView simpleList;
    String actor[] = {"Shah Rukh Khan", "Shushant Singh Rajput", "Sonu Sood", "Kartik Aryan", "Farhan Akhtar", "Hritik Roshan", "Tiger Shrof", "Akshay Kumar", "Ranbir Kapoor", "Saif Ali Khan"};
    int image[] = {R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8, R.drawable.i9, R.drawable.i10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.ContactList);
        com.example.contacts.CustomAdapter customAdapter = new com.example.contacts.CustomAdapter(getApplicationContext(), actor, image);
        simpleList.setAdapter(customAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showImage= new Intent(getApplicationContext(), com.example.contacts.ActivityDetail.class);
                showImage.putExtra("Item_Index",position);
                startActivity(showImage);
            }
        });
    }
}