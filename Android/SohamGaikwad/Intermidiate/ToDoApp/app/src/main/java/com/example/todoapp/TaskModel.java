package com.example.todoapp;
import com.google.firebase.firestore.DocumentId;

public class TaskModel {

    @DocumentId
    private String taskId;
    private String task;
    private boolean isTaskDone;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }
}