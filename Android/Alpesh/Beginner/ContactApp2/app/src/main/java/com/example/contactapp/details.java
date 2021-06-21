package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class details extends AppCompatActivity {

    private String Name,Email,Number,imageid;
    //private Integer ;
    private TextView nameTV, numberTV,emailTV;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Name = getIntent().getStringExtra("ContactName");
        Number = getIntent().getStringExtra("number");
        imageid = getIntent().getStringExtra("imageid");
        Email = getIntent().getStringExtra("Email");

        // initializing our views.
        nameTV = findViewById(R.id.idTVName);
        img = findViewById(R.id.idIVContact);
        numberTV = findViewById(R.id.number);
        emailTV = findViewById(R.id.Email);
        nameTV.setText(Name);
        numberTV.setText(Number);
        numberTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to make a call.
                //makeCall(Number);
                Toast.makeText(getApplicationContext(),"You clicked to call " + Name + "" ,Toast.LENGTH_SHORT).show();
            }
        });
        img.setImageResource(Integer.parseInt(imageid));
        emailTV.setText(Email);
        //callIV = findViewById(R.id.idIVCall);
        //messageIV = findViewById(R.id.idIVMessage);
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}