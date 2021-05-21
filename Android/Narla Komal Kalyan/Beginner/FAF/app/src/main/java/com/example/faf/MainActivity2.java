package com.example.faf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private Button next_button;
    private Button skip_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

        TextView tv1=(TextView) findViewById(R.id.TV1);
        tv1.setText("Facilitates users with tracking system for real time delivery anywhere anytime");

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        skip_button = (Button) findViewById(R.id.skip_button);
        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    public void openActivity3(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}