package com.example.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    private String imageURL;
    private ImageView BasicImage;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewContent);
        textViewAddress = (TextView) findViewById(R.id.textViewFormat);
        BasicImage = (ImageView) findViewById(R.id.imageView);
        imageURL = "http://i.imgur.com/DvpvklR.png";
        Picasso.get().setLoggingEnabled(true);

        //Picasso image
        Picasso.get().load(imageURL).fit().into(BasicImage);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(true);
        qrScan.setBeepEnabled(true);
        qrScan.setCaptureActivity(CaptureActivityPortrait.class);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                textViewName.setText(result.getContents());
                textViewAddress.setText(result.getFormatName());

//                try {
//                    //converting the data to json
//                    JSONObject obj = new JSONObject(result.getContents());
//                    //setting values to textviews
//                    textViewName.setText(obj.getString("name"));
//                    textViewAddress.setText(obj.getString("address"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //if control comes here
//                    //that means the encoded format not matches
//                    //in this case you can display whatever data is available on the qrcode
//                    //to a toast
//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
//                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}