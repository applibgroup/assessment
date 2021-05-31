package com.example.todolistassignment.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todolistassignment.Model.ToDoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private static final String TAG = "ToDoListCRUD";

    private FirebaseFirestore fireDb;

    public DatabaseHandler(Context context){
        fireDb = FirebaseFirestore.getInstance();
    }

    public void insertTask(ToDoModel taskData){
        DocumentReference newTaskRef = fireDb.collection("notes").document();
        taskData.setFireId(newTaskRef.getId());
        newTaskRef.set(taskData);
    }

    public List<ToDoModel> getAllTasks()
    {
        List<ToDoModel> taskList = new ArrayList<>();
        CollectionReference allTasksReference = fireDb.collection("notes");
        allTasksReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map mappedData =  document.getData();
                                ToDoModel newTask = new ToDoModel();
                                newTask.setFireId(document.getId());
                                newTask.setStatus(((Long) mappedData.get("status")).intValue());
                                newTask.setTask((String) mappedData.get("task"));
                                taskList.add(newTask);
                                Log.d(TAG, "Found a Task in Database! Total Found: " + taskList.size());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return taskList;
    }

    public void updateStatus(String fireId, int status){
        fireDb.collection("notes").document(fireId).update("status", status);
    }

    public void updateTask(String fireId, String task){
        fireDb.collection("notes").document(fireId).update("task", task);
    }

    public void deleteTask(String fireId){
        fireDb.collection("notes").document(fireId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
