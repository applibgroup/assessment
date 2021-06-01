package com.example.todoapplication.Model;

public class ToDoModel extends TaskId{

    private String task;
    private String due;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDue() {
        return due;
    }

    public int getStatus() {
        return status;
    }
}
