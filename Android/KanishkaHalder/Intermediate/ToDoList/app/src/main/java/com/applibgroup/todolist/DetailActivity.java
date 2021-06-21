package com.applibgroup.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormatSymbols;
import java.util.UUID;

import io.paperdb.Paper;


public class DetailActivity extends AppCompatActivity {
    Task task;
    private final int ADDITEM_REQUEST_CODE = 130;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         if(getIntent().getSerializableExtra("TASK") != null){
             task = (Task) getIntent().getSerializableExtra("TASK");
             setData(task);

             ImageButton editButton = findViewById(R.id.image_button_edit);
             ImageButton deleteButton = findViewById(R.id.image_button_delete);

             editButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(getApplicationContext(),AddItem.class);
                     intent.putExtra("UPDATE",task);
                     startActivityForResult( intent,ADDITEM_REQUEST_CODE);
                 }
             });

             deleteButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                     dbReference.child(getUserID()).child(String.valueOf(task.getTimeStamp())).removeValue()
                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     finish();
                                 }
                             })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });
                 }
             });


         } else {
             finish();
         }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADDITEM_REQUEST_CODE && data!=null){
            if(data.getStringExtra("STATUS").equals("SUCCESS")){
                Task t = (Task) data.getSerializableExtra("TASK");
                task = t;
                setData(task);
            }
        }
    }

    private String getUserID(){
        String uid;
        Paper.init(getApplicationContext());
        if(Paper.book().contains("id")){
            uid = Paper.book().read("id");
        } else {
            uid = UUID.randomUUID().toString();
            Paper.book().write("id",uid);
        }
        return uid;
    }

    private void setData(Task task){
        ((TextView)findViewById(R.id.text_view_taskname)).setText(task.getTaskName());
        ((TextView)findViewById(R.id.text_view_detail)).setText(task.getDetail());
        String date = task.getDate();
        int startofYear = date.indexOf('-',3)+1;
        int month = Integer.parseInt(date.substring(date.indexOf('-')+1,date.indexOf('-',3)));
        month = month-1;
        date = date.substring(0,2);
        String monthString = new DateFormatSymbols().getMonths()[month];
        date = date +" " + monthString + " " + task.getDate().substring(startofYear) ;
        ((TextView)findViewById(R.id.text_view_date)).setText(date);
        ((TextView)findViewById(R.id.text_view_time)).setText(task.getTime());
    }
}
