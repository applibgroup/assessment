package com.applibgroup.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.applibgroup.contactapp.db.AppDatabase;
import com.applibgroup.contactapp.db.User;

public class ContactDetailActivity extends AppCompatActivity {

    private User globalUser;
    private static final int ADD_USER_REQUEST_CODE = 130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        globalUser = (User)getIntent().getSerializableExtra("USER");

        ImageButton editButton = findViewById(R.id.editUser);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewUserActivity.class);
                intent.putExtra("USER", globalUser);
                startActivityForResult(intent,ADD_USER_REQUEST_CODE);
            }
        });

        ImageButton deleteButton = findViewById(R.id.deleteUser);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase instance = AppDatabase.getINSTANCE(getApplicationContext());
                int pos = getIndexinFavQueue(globalUser);
                if(pos != -1) {
                    FavouritesFragment.favQueue.remove(pos);
                    FavouritesFragment.updateFavQueue();
                }
                instance.userDao().deleteUser(globalUser);
                AllFragment.updateUserList(getApplicationContext());
                finish();
            }
        });

        populateData(globalUser);
    }

    void populateData(User user){
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        ((ImageView)findViewById(R.id.profileImageDisplay)).setImageBitmap(bitmap);

        String phone="";
        if(user.getPhoneNumber()!=null){
            phone = user.getPhoneNumber();
        }
        ((TextView)findViewById(R.id.phoneNumber)).setText(phone);

        String email="";
        if(user.getEmail()!=null){
            email = user.getEmail();
        }
        ((TextView)findViewById(R.id.emailID)).setText(email);

        String firstName = "";
        if(user.getFirstName()!=null){
            firstName = user.getFirstName();
        }
        ((TextView)findViewById(R.id.firstName)).setText(firstName);

        String lastName = "";
        if(user.getLastName()!=null){
            lastName = user.getLastName();
        }
        ((TextView)findViewById(R.id.lastName)).setText(lastName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_USER_REQUEST_CODE){
            if(data != null){
                User user = (User)data.getSerializableExtra("OUTPUT");
                if(user!=null) {
                    int pos = getIndexinFavQueue(globalUser);
                    if(pos != -1)
                        FavouritesFragment.favQueue.remove(pos);
                    globalUser = user;
                    AllFragment.updateUserList(getApplicationContext());
                    if(pos != -1) {
                        FavouritesFragment.favQueue.add(pos, globalUser);
                        FavouritesFragment.updateFavQueue();
                    }
                    populateData(user);
                }

            }
        }
    }

    private int getIndexinFavQueue(User user){
        for(int i=0;i<FavouritesFragment.favQueue.size();i++){
            if(user.getUid() == FavouritesFragment.favQueue.get(i).getUid()){
                return i;
            }
        }
        return -1;
    }
}
