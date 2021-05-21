package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.Adapter.TaskAdapter;
import com.example.todolist.DocumentId.TaskModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DialBox extends AppCompatActivity {

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    List<TaskModel> taskModelList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_box);

        EditText et1= findViewById(R.id.updateEditText);
        String newTask= et1.getText().toString();
        Button bt= findViewById(R.id.updateButton);


//        Intent in= getIntent();
//        String taskId= in.getStringExtra("CurrentTaskId");
//        String val= in.getStringExtra("second");
//        final int currentPosition=Integer.parseInt(val);

//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(newTask.isEmpty()){
//                    Toast.makeText(DialBox.this, "Fields Empty,Please add task first!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//                    db.collection("todoCollection")
//                            .document(taskId).delete()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            taskModelList.remove(currentPosition);
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//
//                    final TaskModel taskModel = new TaskModel();
//
//                    taskModel.setTaskDone(false);
//                    taskModel.setTask(newTask);
//
//                    db.collection("todoCollection")
//                            .add(taskModel)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
////                                    String taskId = documentReference.getId();
//                                    taskModel.setTaskId(taskId);
//                                    taskModelList.add(taskModel);
//                                    adapter.notifyDataSetChanged();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(DialBox.this,"Failed to add the task.",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                    Intent i=new Intent(DialBox.this,MainActivity.class);
//                    startActivity(i);
////                }
//            }
//        });

    }
}