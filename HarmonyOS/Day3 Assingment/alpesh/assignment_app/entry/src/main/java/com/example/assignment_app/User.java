package com.example.assignment_app;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "Data", indices = {@Index(value = {"DB_COLUMN_EMAIL"}, name = "name_index", unique = true)})
public class User extends OrmObject {
    // Set the userId to the auto-generated primary key. Note that the auto-generated primary key takes effect only when the data type is a wrapper class.
    @PrimaryKey(autoGenerate = true)
    private Integer DB_COLUMN_PERSON_ID;
    private String DB_COLUMN_LAST_NAME;
    private String DB_COLUMN_FIRST_NAME;
    private String DB_COLUMN_GENDER;
    private String DB_COLUMN_MOBILE;
    private String DB_COLUMN_EMAIL;
    private String DB_COLUMN_PASSWORD;

    public Integer getDB_COLUMN_PERSON_ID() {
        return DB_COLUMN_PERSON_ID;
    }

    public void setDB_COLUMN_PERSON_ID(Integer DB_COLUMN_PERSON_ID) {
        this.DB_COLUMN_PERSON_ID = DB_COLUMN_PERSON_ID;
    }

    public String getDB_COLUMN_FIRST_NAME() {
        return DB_COLUMN_FIRST_NAME;
    }

    public void setDB_COLUMN_FIRST_NAME(String DB_COLUMN_FIRST_NAME) {
        this.DB_COLUMN_FIRST_NAME = DB_COLUMN_FIRST_NAME;
    }

    public String getDB_COLUMN_LAST_NAME() {
        return DB_COLUMN_LAST_NAME;
    }

    public void setDB_COLUMN_LAST_NAME(String DB_COLUMN_LAST_NAME) {
        this.DB_COLUMN_LAST_NAME = DB_COLUMN_LAST_NAME;
    }

    public String getDB_COLUMN_GENDER() {
        return DB_COLUMN_GENDER;
    }

    public void setDB_COLUMN_GENDER(String DB_COLUMN_GENDER) {
        this.DB_COLUMN_GENDER = DB_COLUMN_GENDER;
    }

    public String getDB_COLUMN_MOBILE() {
        return DB_COLUMN_MOBILE;
    }

    public void setDB_COLUMN_MOBILE(String DB_COLUMN_MOBILE) {
        this.DB_COLUMN_MOBILE = DB_COLUMN_MOBILE;
    }

    public String getDB_COLUMN_EMAIL() {
        return DB_COLUMN_EMAIL;
    }

    public void setDB_COLUMN_EMAIL(String DB_COLUMN_EMAIL) {
        this.DB_COLUMN_EMAIL = DB_COLUMN_EMAIL;
    }

    public String getDB_COLUMN_PASSWORD() {
        return DB_COLUMN_PASSWORD;
    }

    public void setDB_COLUMN_PASSWORD(String DB_COLUMN_PASSWORD) {
        this.DB_COLUMN_PASSWORD = DB_COLUMN_PASSWORD;
    }


    // Add the getter and setter methods.
}