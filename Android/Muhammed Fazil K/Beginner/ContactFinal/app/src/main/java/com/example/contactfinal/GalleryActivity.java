package com.example.contactfinal;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started");
        getIntend();


    }
    private void getIntend(){
        Log.d(TAG, "getIntend: checking intends");
        if(getIntent().hasExtra("image") && getIntent().hasExtra("name") && getIntent().hasExtra("number")){
            Log.d(TAG, "getIntend: intend had found");
            int image=getIntent().getIntExtra("image",0);
            String name=getIntent().getStringExtra("name");
            String number=getIntent().getStringExtra("number");
            setImage(image,name,number);
        }

    }
    private void setImage(int image,String name,String number){
        Log.d(TAG, "setImage: Setting Images");
        ImageView gImage=findViewById(R.id.gallery_image);
        gImage.setImageResource(image);

        TextView gName=findViewById(R.id.gallery_name);
        gName.setText(name);
        TextView gNumber=findViewById(R.id.gallery_number);
        gNumber.setText(number);


    }
}
