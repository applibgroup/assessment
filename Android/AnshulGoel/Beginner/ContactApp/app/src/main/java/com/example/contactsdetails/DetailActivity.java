package com.example.contactsdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    String countryList[] = {"Shikhar Dhawan", "Rohit Sharma", "Virat Kohli", "Suryakumar Yadav", "Rishabh Pant", "Hardik Pandya", "Ravinder Jadeja", "Rahul Chahar", "Jasprit Bumrah", "Bhuvaneshwar Kumar"};
    String contactnumber[]={"0000000000","1111111111","2222222222","3333333333","4444444444","5555555555","6666666666","7777777777","8888888888","9999999999"};
    String Email[]={"sd@gmail.com","rs@gmail.com","vk@gmail.com","sky@gmail.com","rp@gmail.com","hp@gmail.com","rj@gmail.com","rc@gmail.com","jb@gmail.com","bk@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent input= getIntent();
        int index= input.getIntExtra("Item_Index",-1);

        TextView numberTextView;
        numberTextView= findViewById(R.id.numberTextView);

        TextView emailTextView;
        emailTextView= findViewById(R.id.emailTextView);

        if(index > -1){
            int pic=getImg(index);
            ImageView img= findViewById(R.id.details_imgView);
            scaleImg(img,pic);
            String name= countryList[index];
            this.setTitle(name);
            String number= "Contact:   +91 " + contactnumber[index];
            numberTextView.setText(number, TextView.BufferType.EDITABLE);
            String email=  "Email ID:   "  + Email[index];
            emailTextView.setText(email, TextView.BufferType.EDITABLE);
        }
    }

    private int getImg(int index) {
        switch (index){
            case 0: return R.drawable.img1;
            case 1: return R.drawable.img2;
            case 2: return R.drawable.img3;
            case 3: return R.drawable.img4;
            case 4: return R.drawable.img5;
            case 5: return R.drawable.img6;
            case 6: return R.drawable.img7;
            case 7: return R.drawable.img8;
            case 8: return R.drawable.img9;
            case 9: return R.drawable.img11;
            default: return -1;
        }
    }
    private void scaleImg(ImageView img,int pic){
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