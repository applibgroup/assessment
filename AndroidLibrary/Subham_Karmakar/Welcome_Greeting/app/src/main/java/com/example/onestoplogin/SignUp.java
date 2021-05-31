package com.example.onestoplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onestoplogin.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity
{
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            updateUI(currentUser);
        }
        else
        {

        }

        binding.signupEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.emailSignup.getText().toString().isEmpty() || binding.emailPassword.getText().toString().isEmpty() || binding.emailVerifyPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUp.this,"One or more fields empty", Toast.LENGTH_SHORT).show();
                }
                else if(binding.emailPassword.getText().toString().length() < 8)
                {
                    Toast.makeText(SignUp.this,"Password needs to be atleast 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if(binding.emailPassword.getText().toString().equals(binding.emailVerifyPassword.getText().toString()))
                {
                    Toast.makeText(SignUp.this,"Verifying... Please wait!", Toast.LENGTH_SHORT).show();
                    mAuth.createUserWithEmailAndPassword(binding.emailSignup.getText().toString(), binding.emailPassword.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("ABC", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("ABC", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(SignUp.this, "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateUI(FirebaseUser currentUser)
    {
        Intent intent = new Intent(SignUp.this, ActivityLogged.class);
        startActivity(intent);
        finish();
    }
}