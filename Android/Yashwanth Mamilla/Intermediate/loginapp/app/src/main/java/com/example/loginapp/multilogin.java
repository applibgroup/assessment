package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class multilogin extends AppCompatActivity {

    private EditText email_id;
    private EditText password_id;
    private EditText password1_id;
    public Button create_id;
    SignInButton signInButton;
    private ImageView icon_pic;
    public int t;
    private static final String TAG = "multilogin";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multilogin);
        email_id=findViewById(R.id.email_text);
        password_id=findViewById(R.id.password_text);
        password1_id=findViewById(R.id.password1_text);
        icon_pic=findViewById(R.id.imageView);
        create_id = findViewById(R.id.cb_button);
        signInButton=findViewById(R.id.gb);
        mAuth = FirebaseAuth.getInstance();
        Intent intent=getIntent();
        t = intent.getIntExtra(MainActivity.EXTRA_MESSAGE,4);
        reload(t);
    }
    public void reload(int t){
        if(t==4){
            if(create_id==null)
                Log.d(TAG, "Error check create button");
             create_id.setText("Create");
            signInButton.setVisibility(View.INVISIBLE);
            //setContentView(R.layout.activity_multilogin);
        }
        if(t==3){
            icon_pic.setImageResource(R.drawable.google_icon);
            create_id.setText("Login");
            email_id.setText("Enter email ID");
            password_id.setText("Enter Password");
            password1_id.setVisibility(View.INVISIBLE);
            create_id.setVisibility(View.INVISIBLE);
            // Configure Google Sign In
           /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            */
        }

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
    @Override
    public void onBackPressed(){
        Intent box=new Intent(this,MainActivity.class);
        box.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(box);
        finish();
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
                            Toast.makeText(multilogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    public void updateUI(FirebaseUser user){
        if(user!=null){
            Intent box=new Intent(this,home_activity.class);
            box.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(box);
            finish();
        }

    }
    /*private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(multilogin.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }*/
    public void Register(View view) {
        Log.d(TAG, "Inside onclick");
        if(t==4)
        {
            Log.d(TAG, "Inside onclick");
            String email_str = email_id.getText().toString();
            String password_str = password_id.getText().toString();
            String password1_str = password1_id.getText().toString();
            if(!email_str.isEmpty() && !password_str.isEmpty() && !password1_str.isEmpty()){
                if(password_str.compareTo(password1_str)==0 )
                    createAccount(email_str,password_str);
                else
                    Toast.makeText(multilogin.this, "Passwords Didn't Match" ,
                            Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(multilogin.this, "Enter the Details correctly",
                        Toast.LENGTH_SHORT).show();
            }
            password_id.getText().clear();
            password1_id.getText().clear();

        }
        if(t==3)
        {
           // signIn();
        }
    }
}