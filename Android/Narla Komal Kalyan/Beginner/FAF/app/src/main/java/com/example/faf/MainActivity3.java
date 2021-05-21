package com.example.faf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();

        TextView tv1=(TextView) findViewById(R.id.TV1);
        tv1.setText("We guarantee your service delivery will be done by one business day");
    }
}