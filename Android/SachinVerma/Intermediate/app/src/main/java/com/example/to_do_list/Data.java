package com.example.to_do_list;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Data implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    private String id;
    private String a, b, c, d, e;

    public Data() {

    }

    public Data(String a, String b, String c, String d, String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public String getTask() {
        return a;
    }

//    public void setTask(String a) {
//        this.a = a;
//    }

    public String getDetails() {
        return b;
    }

//    public void setDetails(String b) {
//        this.b = b;
//    }

    public String getDate() {
        return c;
    }

//    public void setDate(String c) {
//        this.c = c;
//    }

    public String getTime() {
        return d;
    }

//    public void setTime(String d) {
//        this.d = d;
//    }

    public String getType() { return e; }
}
