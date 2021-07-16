package android.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView textViewname,textViewphone,textViewemailid;
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewname = findViewById(R.id.name);
        textViewphone = findViewById(R.id.phone);
        textViewemailid = findViewById(R.id.emailid);
        photo = findViewById(R.id.profilephoto);
        Intent intent = getIntent();
        textViewname.setText(intent.getStringExtra("yogeshcontactapp.name"));
        textViewphone.setText(intent.getStringExtra("yogeshcontactapp.phone"));
        textViewemailid.setText(intent.getStringExtra("yogeshcontactapp.emailid"));
        photo.setImageResource(intent.getIntExtra("yogeshcontactapp.photoid",0));
    }
}