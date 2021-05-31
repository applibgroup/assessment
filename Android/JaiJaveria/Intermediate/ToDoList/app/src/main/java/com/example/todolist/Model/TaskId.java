package com.example.todolist.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TaskId {
    @Exclude
    public String taskID;
    public <T extends TaskId> T withID(@NonNull final String id)
    {
        this.taskID=id;
        return  (T) this;
    }

}
