package com.example.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<models> taskModelList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextTask = findViewById(R.id.editTextTask);
        final EditText editTextTime = findViewById(R.id.editTextTime);
        final EditText editTextDate = findViewById(R.id.editTextDate);
        Button addTaskBtn = findViewById(R.id.addTaskBtn);

        taskModelList = new ArrayList<>();

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTask = editTextTask.getText().toString();
                String newDate = editTextDate.getText().toString();
                String newTime = editTextTime.getText().toString();

                if(newTask.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please add task first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final models taskModel = new models();

                taskModel.setTaskDone(false);
                taskModel.setTask(newTask);
                taskModel.setDate(newDate);
                taskModel.setTime(newTime);

                db.collection("todoCollection")
                        .add(taskModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String taskId = documentReference.getId();
                                taskModel.setTaskId(taskId);
                                taskModelList.add(taskModel);
                                adapter.notifyDataSetChanged();
                                editTextTask.setText(null);
                                editTextDate.setText(null);
                                editTextTimebuy clothes.setText(null);
                            }
                        });
            }
        });

        RecyclerView taskRecyclerView  = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this, taskModelList, db);
        taskRecyclerView.setAdapter(adapter);

        db.collection("todoCollection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                models taskModel = document.toObject(models.class);
                                taskModelList.add(taskModel);
                            }
                            adapter.notifyDataSetChanged();

                        }else{
                            Toast.makeText(MainActivity.this, "Error Getting task list.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}