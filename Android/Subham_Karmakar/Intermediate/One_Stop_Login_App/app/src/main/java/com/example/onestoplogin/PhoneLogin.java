package com.example.onestoplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private TextView phoneNo;
    private TextView otpText;
    private Button sendButton;
    private Button verifyOtp;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        mAuth = FirebaseAuth.getInstance();

        phoneNo = findViewById(R.id.phone_no);
        sendButton = findViewById(R.id.otp);
        otpText = findViewById(R.id.verify_otp);
        verifyOtp = findViewById(R.id.verify);
        info = findViewById(R.id.info_otp);

        info.setVisibility(View.GONE);
        otpText.setVisibility(View.GONE);
        verifyOtp.setVisibility(View.GONE);
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

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(phoneNo.getText().length() != 10)
                {
                    Toast.makeText(PhoneLogin.this,"Please enter a valid Phone Number!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    info.setVisibility(View.VISIBLE);
                    otpText.setVisibility(View.VISIBLE);
                    verifyOtp.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phoneNo.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            PhoneLogin.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                            {
                                @Override
                                public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential)
                                {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e)
                                {
                                    sendButton.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                                {


                                    verifyOtp.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if(otpText.getText().length()<6)
                                            {
                                                Toast.makeText(PhoneLogin.this,"Enter a valid OTP", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                            {
                                                Toast.makeText(PhoneLogin.this,"Verifying... Please wait!", Toast.LENGTH_SHORT).show();
                                                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(s, otpText.getText().toString());
                                                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                                        {
                                                            @Override
                                                            public void onComplete(@NonNull @NotNull Task<AuthResult> task)
                                                            {
                                                                if(task.isSuccessful())
                                                                {
                                                                    Intent intent = new Intent(PhoneLogin.this, ActivityLogged.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(PhoneLogin.this,"Failed", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }

                                        }
                                    });
                                }
                            }
                    );
                }

            }
        });


    }

    private void updateUI(FirebaseUser currentUser)
    {
        Intent intent = new Intent(PhoneLogin.this, ActivityLogged.class);
        startActivity(intent);
        finish();
    }
}