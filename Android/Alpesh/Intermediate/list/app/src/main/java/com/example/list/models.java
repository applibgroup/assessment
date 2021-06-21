package com.example.list;

import com.google.firebase.firestore.DocumentId;

public class models {
    @DocumentId
    private String taskId;
    private String task;
    private String date;
    private String time;
    private boolean isTaskDone;

   /* public models(){}
    private models(String task, String date, String time)
    {
        this.date=date;
        this.task=task;
        this.time=time;
    }*/

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
