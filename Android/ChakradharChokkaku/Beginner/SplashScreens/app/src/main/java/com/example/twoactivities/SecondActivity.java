package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            SecondActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void launchThirdActivity(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        Log.d(LOG_TAG, "Button clicked!");
        startActivity(intent);
    }
}