package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<TaskModel> taskModelList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextTask = findViewById(R.id.editTextTask);
        Button addTaskBtn = findViewById(R.id.addTaskBtn);

        taskModelList = new ArrayList<>();

        addTaskBtn.setOnClickListener(v -> {

            String newTask = editTextTask.getText().toString();

            if(newTask.isEmpty()){
                Toast.makeText(MainActivity.this, "Please add task first.", Toast.LENGTH_SHORT).show();
                return;
            }

            final TaskModel taskModel = new TaskModel();

            taskModel.setTaskDone(false);
            taskModel.setTask(newTask);

            db.collection("todoCollection")
                    .add(taskModel)
                    .addOnSuccessListener(documentReference -> {
                        String taskId = documentReference.getId();
                        taskModel.setTaskId(taskId);
                        taskModelList.add(taskModel);
                        adapter.notifyDataSetChanged();
                        editTextTask.setText(null);
                    });
        });

        RecyclerView taskRecyclerView  = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this, taskModelList, db);
        taskRecyclerView.setAdapter(adapter);

        db.collection("todoCollection")
                .get()
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            TaskModel taskModel = document.toObject(TaskModel.class);
                            taskModelList.add(taskModel);
                        }
                        adapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(MainActivity.this, "Error Getting task list.", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}