package com.example.firstapplication;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"email"}, name = "email_index", unique = true)})
public class User extends OrmObject {

    // Set the userId to the auto-generated primary key.
    // Note that the auto-generated primary key takes effect only when the data type is a wrapper class.
    @PrimaryKey(autoGenerate = true)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobile;

    // true - male, false - female
    private Boolean gender;

    // Add the getter and setter methods.
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}