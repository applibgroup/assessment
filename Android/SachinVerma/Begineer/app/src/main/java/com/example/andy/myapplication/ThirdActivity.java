package com.example.andy.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.andy.myapplication.R.layout.activity_third;

public class ThirdActivity extends AppCompatActivity {
    TextView a, b, c;
    ImageView i;
    ArrayList<String> List = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_third);
        List.clear();
        int x = R.drawable.ic_launcher_background;
        a = findViewById(R.id.textView);
        b = findViewById(R.id.textView2);
        c = findViewById(R.id.textView3);
        i = findViewById(R.id.imageView);
        Intent intent = getIntent();
        List = intent.getStringArrayListExtra("data");
        a.setText(List.get(0));
        b.setText(List.get(1));
        c.setText(List.get(2));

        URL url = null;
        try {
            url = new URL("https://www.bing.com/th?id=OIP.4pgXINVL1ceGntSRhHPb9QHaEx");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        i.setImageBitmap(bmp);
//        tittle.setText(List.get(0));

//          if(imag != null) {
//              imag.setImageDrawable(subjectData.Image.getImage());
//          }
;
    }
}