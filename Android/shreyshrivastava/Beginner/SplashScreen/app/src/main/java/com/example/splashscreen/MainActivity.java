package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int flag = 1;
    static int SPLASH_SCREEN_TIME_OUT=5000;

    ImageView image;
    TextView heading, subtitle;
    Button skipButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);

        heading = findViewById(R.id.textView2);
        subtitle = findViewById(R.id.textView3);

        skipButton =  findViewById(R.id.button);
        nextButton = findViewById(R.id.button2);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    image.setImageResource(R.drawable.pizza);
                    heading.setText("Pizza");
                    subtitle.setText("Is not healthy and delicious");
                    flag = 2;
                } else if (flag == 2) {
                    image.setImageResource(R.drawable.icecream);
                    heading.setText("Ice cream");
                    subtitle.setText("Is not healthy and delicious");
                    flag = 3;
                } else if (flag == 3) {
                    openNewActivity();
                }

            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}