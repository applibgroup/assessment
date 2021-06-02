package com.applibgroup.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applibgroup.contactapp.db.AppDatabase;
import com.applibgroup.contactapp.db.User;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class AddNewUserActivity extends AppCompatActivity {

    private Uri profileImageUri;
    private byte[] imageBytes;
    private String email,phoneNumber,firstName,lastName;

    private ImageView imageView;
    private Button saveButton;

    private static final int ADD_USER_REQUEST_CODE = 130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        imageView = findViewById(R.id.profileImageView);
        imageView.setImageDrawable(getDrawable(R.drawable.avatar2));

        User user = null;
        user = (User)getIntent().getSerializableExtra("USER");

        if(user != null){
            firstName = user.getFirstName();
            lastName = user.getLastName();
            imageBytes = user.getImage();
            email = user.getEmail();
            phoneNumber = user.getPhoneNumber();
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);

            ((TextView)findViewById(R.id.firstNameInput)).setText(firstName);
            ((TextView)findViewById(R.id.lastNameInput)).setText(lastName);
            ((TextView)findViewById(R.id.phoneInput)).setText(phoneNumber);
            ((TextView)findViewById(R.id.emailInput)).setText(email);
            imageView.setImageBitmap(bitmap);

        } else {
            //Setting default image
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar2);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // what 90 does ??
            imageBytes = stream.toByteArray();
        }

        Button imagePickerBtn = findViewById(R.id.imagePickerBtn);
        imagePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddNewUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        saveButton = findViewById(R.id.saveButton);
        final User finalUser = user;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase instance = AppDatabase.getINSTANCE(getApplicationContext());

                firstName =  ((TextView)findViewById(R.id.firstNameInput)).getText().toString().trim();
                lastName =  ((TextView)findViewById(R.id.lastNameInput)).getText().toString().trim();
                phoneNumber = ((TextView)findViewById(R.id.phoneInput)).getText().toString().trim();
                email = ((TextView)findViewById(R.id.emailInput)).getText().toString().trim();
                if(!phoneNumber.equals("") && !firstName.equals("") && !lastName.equals("")) {
                    if(finalUser != null){
                        finalUser.setImage(imageBytes);
                        finalUser.setFirstName(firstName);
                        finalUser.setLastName(lastName);
                        finalUser.setPhoneNumber(phoneNumber);
                        finalUser.setEmail(email);
                        instance.userDao().updateUsers(finalUser);
                        Intent intent = new Intent();
                        intent.putExtra("OUTPUT", finalUser);
                        setResult(ADD_USER_REQUEST_CODE, intent);
                        finish();
                    } else {
                        User userTemp = new User(imageBytes, firstName, lastName, phoneNumber, email);
                        instance.userDao().insertUser(userTemp);
                        Intent intent = new Intent();
                        intent.putExtra("OUTPUT", "SUCESS");
                        setResult(ADD_USER_REQUEST_CODE, intent);
                        finish();
                    }
                } else {
                    Toast.makeText(AddNewUserActivity.this, "Enter all contact info", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            //Image Uri will not be null for RESULT_OK
            try {
                Uri uri = data.getData();
                if(uri != null) {
                    profileImageUri = uri;
                    Log.d("IMAGE PICKER", "onActivityResult: " + data.getData());
                    InputStream iStream = getContentResolver().openInputStream(uri);
                    imageBytes = getBytes(iStream);
                    imageView.setImageURI(uri);
                }
                else{
                    Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
