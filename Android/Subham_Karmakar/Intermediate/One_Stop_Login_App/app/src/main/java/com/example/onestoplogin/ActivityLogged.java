package com.example.onestoplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogged extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private Button logout;
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        logout = findViewById(R.id.logout);
        username = findViewById(R.id.user_name);
        username.setText("Name: " + currentUser.getDisplayName() + "\n"+ "Email id: " + currentUser.getEmail() + "\n" + "Photo URL :" + currentUser.getPhotoUrl() + "\n" + "Is Email Verified: " + currentUser.isEmailVerified() + "\n" + "Phone Number: " + currentUser.getPhoneNumber());

        if(currentUser != null)
        {

        }
        else
        {
            updateUI(currentUser);
        }

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                updateUI(currentUser);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser)
    {
        Intent intent = new Intent(ActivityLogged.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}