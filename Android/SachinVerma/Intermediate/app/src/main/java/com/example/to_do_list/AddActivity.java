package com.example.to_do_list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class AddActivity extends AppCompatActivity {

    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText task;
    private EditText details;
    private EditText date;
    private EditText time;
    private CheckBox travel;
    private CheckBox shopping;
    private CheckBox gym;
    private CheckBox party;
    private CheckBox others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        task = findViewById(R.id.editText_add);
        details = findViewById(R.id.editText_add2);
        date = findViewById(R.id.editText_add3);
        time = findViewById(R.id.editText_add4);
        travel = findViewById(R.id.checkBox1);
        shopping = findViewById(R.id.checkBox2);
        gym = findViewById(R.id.checkBox3);
        party = findViewById(R.id.checkBox4);
        others = findViewById(R.id.checkBox5);
    }


    public void InsertIntoFirebase(View view) {
        String Task = task.getText().toString();
        String Details = details.getText().toString();
        String Date = date.getText().toString();
        String Time = time.getText().toString();

        String Type = null;
        if(travel.isChecked()) Type = "travel";
        if(shopping.isChecked()) Type = "shopping";
        if(gym.isChecked()) Type = "gym";
        if(party.isChecked()) Type = "party";
        if(others.isChecked()) Type = "others";

        Log.d("AddActivity", "TASK : " + Task);
        Log.d("AddActivity", "DETAILS : " + Details);
        Log.d("AddActivity", "Date : " + Date);
        Log.d("AddActivity", "Time : " + Time);
        Log.d("AddActivity", "Type : " + Type);

        Data data = new Data(Task, Details, Date, Time, Type);
        CollectionReference dbData = db.collection("Data");

//        Data data = new Data(Task, Details, Date, Time);
        dbData.add(data).addOnSuccessListener(documentReference -> {
            Toast.makeText(this,"Your Task has been added", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this,"Some Error occurred", Toast.LENGTH_SHORT).show();
        });
        finish();
    }

    public void CancelActivity(View view) {
        finish();
    }
}
