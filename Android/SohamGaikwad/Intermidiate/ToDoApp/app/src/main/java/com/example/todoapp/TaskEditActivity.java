package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TaskEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit_view);

        Button updateTaskBtn = findViewById(R.id.updateTaskBtn);


        updateTaskBtn.setOnClickListener(v -> {

        });

    }
}
