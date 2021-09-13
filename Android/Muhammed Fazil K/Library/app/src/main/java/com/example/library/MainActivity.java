package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView img1,img2,img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1=findViewById(R.id.img1);
        String url="https://cdn-japantimes.com/wp-content/uploads/2021/01/np_file_63536.jpeg";
        Picasso.get().load(url).into(img1);

        img2=findViewById(R.id.img2);
        String url2="https://cdn.vox-cdn.com/thumbor/eM-rPoHs-Nke_ZgNbn1xul0zzuc=/0x352:4075x3434/1200x800/filters:focal(1521x983:2309x1771)/cdn.vox-cdn.com/uploads/chorus_image/image/67756714/1211599924.0.jpg";
        Picasso.get().load(url2).into(img2);

        img3=findViewById(R.id.img3);
        String url3="https://i2-prod.football.london/incoming/article18887838.ece/ALTERNATES/s1200c/1_Mesut-Ozil.jpg";
        Picasso.get().load(url3).into(img3);




    }
}