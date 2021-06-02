package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home_activity extends AppCompatActivity {

    private FirebaseAuth mAuth1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.activity_home);

    }

    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent box=new Intent(this,MainActivity.class);
        box.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(box);
        finish();
    }
}