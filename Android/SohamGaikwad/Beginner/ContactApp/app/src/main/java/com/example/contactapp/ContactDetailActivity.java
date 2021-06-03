package com.example.contactapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        // on below line we are getting data which
        // we passed in our adapter class with intent.
        String contactName = getIntent().getStringExtra("name");
        String contactNumber = getIntent().getStringExtra("contact");
        String contactEmail = getIntent().getStringExtra("email");
        Bundle extras = getIntent().getExtras();
        String fileName = extras.getString("image");
        File filePath = getFileStreamPath(fileName);
        Drawable d = Drawable.createFromPath(filePath.toString());

        // initializing our views.
        TextView nameTV = findViewById(R.id.idTVName);
        ImageView contactIV = findViewById(R.id.idIVContact);
        TextView contactTV = findViewById(R.id.idTVPhone);
        nameTV.setText(contactName);
        contactTV.setText(contactNumber);
        contactIV.setBackground(d);

        TextView emailTV = findViewById(R.id.idTVEmail);
        emailTV.setText(contactEmail);
    }
}
