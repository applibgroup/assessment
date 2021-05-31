package com.example.onestoplogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onestoplogin.databinding.ActivityLoggedInBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ActivityLogged extends AppCompatActivity
{
    private ActivityLoggedInBinding binding;
    private FirebaseAuth mAuth;

    String url = "https://picsum.photos/1000";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logged_in);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateImage();


        binding.userName.setText("Name: " + currentUser.getDisplayName() + "\n"+ "Email id: " + currentUser.getEmail() + "\n" + /*"Photo URL :" + currentUser.getPhotoUrl() + "\n" + */"Is Email Verified: " + currentUser.isEmailVerified() + "\n" + "Phone Number: " + currentUser.getPhoneNumber());

        if(currentUser != null)
        {

        }
        else
        {
            updateUI(currentUser);
        }

        binding.logout.setOnClickListener(new View.OnClickListener()
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

    private void updateImage()
    {
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;

        Picasso.get().load(url).resize(width - 200, width - 200)
                .centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(binding.randomImage);
    }
}