package com.applib.loginsignupapp;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "UserTable", ignoredColumns = {"ignoredColumn1", "ignoredColumn2"},
        indices = {@Index(value = {"emailID"}, name = "email_index", unique = true)})
public class UserModel extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer uid;
    private String firstname;
    private String lastName;
    private String emailID;
    private String phoneNumber;
    private String password;
    private String gender;

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid){
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
