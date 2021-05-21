package android.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    public String tasktitle;
    public String taskdetails;
    public String taskdate;
    public String tasktime;
    public EditText title;
    public EditText details;
    public EditText date;
    public EditText time;
    public Button button;
    public FirebaseFirestore firestore2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firestore2 = FirebaseFirestore.getInstance();
        title = findViewById(R.id.titleedit);
        details = findViewById(R.id.detailsedit);
        date = findViewById(R.id.dateedit);
        time = findViewById(R.id.timeedit);
        button = findViewById(R.id.savebtn);
    }

    public void savereturn(View v){
        tasktitle = title.getText().toString();
        taskdetails = details.getText().toString();
        taskdate = date.getText().toString();
        tasktime = time.getText().toString();
        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("tasktitle",tasktitle);
        taskMap.put("taskdetails",taskdetails);
        taskMap.put("taskdate",taskdate);
        taskMap.put("tasktime",tasktime);
        taskMap.put("timestamp", FieldValue.serverTimestamp());
        firestore2.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity2.this, "Task has been saved !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        finish();
    }
}