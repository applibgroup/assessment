package com.example.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDetail extends AppCompatActivity {

    String actor[] = {"Shah Rukh Khan", "Shushant Singh Rajput", "Sonu Sood", "Kartik Aryan", "Farhan Akhtar", "Hritik Roshan", "Tiger Shrof", "Akshay Kumar", "Ranbir Kapoor", "Saif Ali Khan"};
    String contactnumber[]={"0000000000","1111111111","2222222222","3333333333","4444444444","5555555555","6666666666","7777777777","8888888888","9999999999"};
    String Email[]={"srk@gmail.com","ssr@gmail.com","ss@gmail.com","ka@gmail.com","fa@gmail.com","hr@gmail.com","ts@gmail.com","ak@gmail.com","rk@gmail.com","sak@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent input= getIntent();
        int index= input.getIntExtra("Item_Index",-1);

        TextView nameTextView;
        nameTextView= findViewById(R.id.ActorName);

        TextView numberTextView;
        numberTextView= findViewById(R.id.numberTextView);

        TextView emailTextView;
        emailTextView= findViewById(R.id.emailTextView);

        if(index > -1){
            int pic=getImg(index);
            ImageView img= findViewById(R.id.details_imgView);
            scaleImg(img,pic);
            String name= actor[index];
            nameTextView.setText(name, TextView.BufferType.EDITABLE);
            String number= "+91 " + contactnumber[index];
            numberTextView.setText(number, TextView.BufferType.EDITABLE);
            String email=  Email[index];
            emailTextView.setText(email, TextView.BufferType.EDITABLE);
        }
    }

    private int getImg(int index) {
        switch (index){
            case 0: return R.drawable.i1;
            case 1: return R.drawable.i2;
            case 2: return R.drawable.i3;
            case 3: return R.drawable.i4;
            case 4: return R.drawable.i5;
            case 5: return R.drawable.i6;
            case 6: return R.drawable.i7;
            case 7: return R.drawable.i8;
            case 8: return R.drawable.i9;
            case 9: return R.drawable.i10;
            default: return -1;
        }
    }
    private void scaleImg(ImageView img, int pic){
        Display screen=getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options= new BitmapFactory.Options();

        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),pic,options);

        int imgWidth= options.outWidth;
        int screenWidth= screen.getWidth();

        if(imgWidth>screenWidth){
            int ratio= Math.round((float)imgWidth/ (float)screenWidth);
            options.inSampleSize=ratio;
        }
        options.inJustDecodeBounds=false;
        Bitmap scaleImg=BitmapFactory.decodeResource(getResources(),pic,options);
        img.setImageBitmap(scaleImg);
    }

}