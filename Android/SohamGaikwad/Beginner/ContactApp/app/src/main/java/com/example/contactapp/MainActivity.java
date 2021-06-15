package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        addContacts();
    }

    private void addContacts() {
        contacts.add(new Contact("Harry", "123456", "harry@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Hermione", "123456", "hermione@gmail.com", R.drawable.hermione));
        contacts.add(new Contact("Ron", "123456", "ron@gmail.com", R.drawable.ron));
        contacts.add(new Contact("Dumbledore", "123456", "dumble@gmail.com", R.drawable.dumbledore));
        contacts.add(new Contact("Harry2", "123456", "harry2@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry4", "123456", "harry4@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry5", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry6", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry7", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry8", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry9", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry10", "123456", "harry5@gmail.com", R.drawable.harry));
        contacts.add(new Contact("Harry11", "123456", "harry5@gmail.com", R.drawable.harry));

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contact_list);
        ContactAdapter contactAdapter = new ContactAdapter(this, contacts);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}