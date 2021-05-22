package com.example.contactapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ContactDetailActivity extends AppCompatActivity {
    private String contactName, contactNumber, contactEmail;
    private TextView contactTV, nameTV, emailTV;
    private ImageView contactIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        // on below line we are getting data which
        // we passed in our adapter class with intent.
        contactName = getIntent().getStringExtra("name");
        contactNumber = getIntent().getStringExtra("contact");
        contactEmail = getIntent().getStringExtra("email");
        Bundle extras = getIntent().getExtras();
        String fileName = extras.getString("image");
        File filePath = getFileStreamPath(fileName);
        Drawable d = Drawable.createFromPath(filePath.toString());

        // initializing our views.
        nameTV = findViewById(R.id.idTVName);
        contactIV = (ImageView) findViewById(R.id.idIVContact);
        contactTV = findViewById(R.id.idTVPhone);
        nameTV.setText(contactName);
        contactTV.setText(contactNumber);
        contactIV.setBackground(d);

        emailTV = findViewById(R.id.idTVEmail);
        emailTV.setText(contactEmail);
    }
}
