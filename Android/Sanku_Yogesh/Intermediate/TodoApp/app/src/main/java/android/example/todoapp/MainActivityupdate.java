package android.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivityupdate extends AppCompatActivity {
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
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityupdate);
        firestore2 = FirebaseFirestore.getInstance();
        title = findViewById(R.id.titleedit1);
        details = findViewById(R.id.detailsedit1);
        date = findViewById(R.id.dateedit1);
        time = findViewById(R.id.timeedit1);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        details.setText(intent.getStringExtra("details"));
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));
        id = intent.getStringExtra("id");
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
        taskMap.put("id",id);
        firestore2.collection("task").document(id).update(taskMap);
        finish();
    }

    public void deletereturn(View v){
        firestore2.collection("task").document(id).delete();
        finish();
    }
}