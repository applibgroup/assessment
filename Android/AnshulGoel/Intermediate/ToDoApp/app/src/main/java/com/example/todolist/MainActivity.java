package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todolist.Adapter.TaskAdapter;
import com.example.todolist.DocumentId.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db= FirebaseFirestore.getInstance();;
    List<TaskModel> taskModelList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextTask = findViewById(R.id.editTextTask);
        Button addTaskBtn = findViewById(R.id.addTaskBtn);

        taskModelList = new ArrayList<>();

//        db=FirebaseFirestore.getInstance();

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTask = editTextTask.getText().toString();

                if(newTask.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields Empty,Please add task first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final TaskModel taskModel = new TaskModel();

                taskModel.setTaskDone(false);
                taskModel.setTask(newTask);

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
                            }
                        });
            }
        });

        RecyclerView taskRecyclerView  = findViewById(R.id.taskRecyclerView);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration= new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        adapter = new TaskAdapter(MainActivity.this, taskModelList, db);
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.addItemDecoration(itemDecoration);

//        db=FirebaseFirestore.getInstance();
        db.collection("todoCollection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TaskModel taskModel = document.toObject(TaskModel.class);
                                taskModelList.add(taskModel);
                            }
                            adapter.notifyDataSetChanged();

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error Getting task list.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}