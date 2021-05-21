package com.example.intermediate;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ToDo {

    private String type;
    private String task;
    private String detail;
    private String datetime;

    public ToDo() {}

    public ToDo(String task, String detail, String datetime, String type) {
        this.task=task;
        this.detail=detail;
        this.datetime=datetime;
        this.type=type;
    }

    public String getTask() {
        return task;
    }

    public String getDetails() {
        return detail;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getType() {
        return type;
    }


}
