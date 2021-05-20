package com.applibgroup.todolist;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Task implements Serializable {
    private long timeStamp;
    private String taskName;
    private String detail;
    private String date;
    private String time;
    private String type;

    public Task(){
    }

    public Task(String taskName, String detail, String date, String time, String type, long timeStamp) {
        this.taskName = taskName;
        this.detail = detail;
        this.date = date;
        this.time = time;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
