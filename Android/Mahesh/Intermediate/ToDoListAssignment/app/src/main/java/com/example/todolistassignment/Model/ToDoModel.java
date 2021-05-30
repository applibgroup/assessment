package com.example.todolistassignment.Model;

public class ToDoModel {
    private int status;
    private String task;
    private String fireId;
 
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFireId() {return fireId; }

    public void setFireId(String fireId) { this.fireId = fireId; }

}