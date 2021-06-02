package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button fbbutton;
    private Button twbutton;
    private Button gbutton;
    private EditText email_id;
    private EditText password_id;

   // private static final String TAG = "GoogleActivity";
   // private static final int RC_SIGN_IN = 9001;
   public static final String EXTRA_MESSAGE =
           "com.example.android.twoactivities.extra.MESSAGE";
   private static final String TAG = "MainActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    /* [END declare_auth] */

    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        loginbutton=findViewById(R.id.login_button);
        fbbutton=findViewById(R.id.fb_button);
        twbutton=findViewById(R.id.tw_button);
        gbutton=findViewById(R.id.google_button);
        email_id=findViewById(R.id.emailtext);
        password_id=findViewById(R.id.passwordtext);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //startActivity(intent);
                    String email_str = email_id.getText().toString();
                    String password_str = password_id.getText().toString();
                    if (!email_str.isEmpty() && !password_str.isEmpty())
                        signIn(email_str, password_str);
                    else {
                        Toast.makeText(MainActivity.this, "Enter the email address and password",
                                Toast.LENGTH_SHORT).show();
                    }
            }
        });
        gbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressed_google_button();
            }
        });

        // Initialize Firebase Auth

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            FirebaseAuth.getInstance().signOut();
            //reload(currentUser);

        }
        email_id.getText().clear();
        password_id.getText().clear();
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        email_id.getText().clear();
                        password_id.getText().clear();
                    }
                });
        // [END sign_in_with_email]
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void reload(FirebaseUser user) {
        Intent intent = new Intent(this, home_activity.class);
        String name = user.getDisplayName();
        intent.putExtra(EXTRA_MESSAGE, name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void updateUI(FirebaseUser user) {

        if(user!=null) {
            Intent intent = new Intent(this, home_activity.class);
            String name = user.getDisplayName();
            intent.putExtra(EXTRA_MESSAGE, name);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void pressed_google_button(){
        Intent box = new Intent(this,multilogin.class);
        box=box.putExtra(EXTRA_MESSAGE,3);
        box.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(box);
        finish();
    }

    public void Register(View view) {
         Intent box = new Intent(this, multilogin.class);
        box=box.putExtra(EXTRA_MESSAGE,4);
        box.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(box);
        finish();
/*
        String email_str = email_id.getText().toString();
        String password_str = password_id.getText().toString();
        if(!email_str.isEmpty() && !password_str.isEmpty())
            createAccount(email_str,password_str);
        else{
            Toast.makeText(MainActivity.this, "Enter the email address and password",
                    Toast.LENGTH_SHORT).show();

        }
        email_id.getText().clear();
        password_id.getText().clear();*/
    }
}