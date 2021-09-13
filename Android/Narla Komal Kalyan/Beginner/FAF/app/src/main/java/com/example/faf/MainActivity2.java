package com.example.faf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button next_button = findViewById(R.id.next_button);
        next_button.setOnClickListener(v -> openActivity3());

        Button skip_button = findViewById(R.id.skip_button);
        skip_button.setOnClickListener(v -> openActivity3());
    }

    public void openActivity3(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}