package com.example.toplibrariesassignment.Model;

import java.io.Serializable;

public class VisitorComment implements Serializable {
    String comment;
    public VisitorComment(String comment)
    {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }
}