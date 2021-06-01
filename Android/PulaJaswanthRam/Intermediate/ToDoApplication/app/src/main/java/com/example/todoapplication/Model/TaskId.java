package com.example.todoapplication.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TaskId {
    @Exclude
    public String TaskId;       // to exclude taskId from all the manipulations

    public <T extends TaskId> T withId(@NonNull final  String id){
        this.TaskId = id;
        return (T) this;
    }
}
