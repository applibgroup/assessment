package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread background = new Thread(){
            public void run(){
                try{
                    sleep(3*1000);
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
                catch (Exception e){
                    Log.d("DEBUG",e.getMessage());
                }
            }
        };
        background.start();
    }
}