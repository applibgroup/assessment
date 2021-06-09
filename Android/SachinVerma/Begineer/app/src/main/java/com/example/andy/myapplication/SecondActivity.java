package com.example.andy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            SecondActivity.class.getSimpleName();
    DBHelper db = new DBHelper(this);

    private EditText person_name;
    private EditText person_phone_no;
    private EditText person_email_id;
    private EditText person_profile_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        person_name = findViewById(R.id.editText_second);
        person_phone_no = findViewById(R.id.editText2_second);
        person_email_id = findViewById(R.id.editText3_second);
        person_profile_link = findViewById(R.id.editText4_second);
    }

    public void insertIntoDatabase(View view) {
        Log.d(LOG_TAG,"Add button clicked");
        String pn = person_name.getText().toString();
        String ppn = person_phone_no.getText().toString();
        String pid = person_email_id.getText().toString();
        String ppl = person_profile_link.getText().toString();

        Log.d(LOG_TAG,"id : " + pid);

        boolean insertion = db.insert_new_contact(pn, ppn, pid, ppl);
        if(insertion) {
            Toast.makeText(this, "Contact has been successfully added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        else {
            Toast.makeText(this, "Contact addition failed", Toast.LENGTH_SHORT).show();
        }
    }
}