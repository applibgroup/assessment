package com.example.pictoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread background = new Thread(){
            public void run(){
                try{
                    sleep(4*1000);
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
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