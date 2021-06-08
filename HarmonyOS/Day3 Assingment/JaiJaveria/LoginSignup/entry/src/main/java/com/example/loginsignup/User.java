package com.example.loginsignup;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "user")
public class User extends OrmObject {
    private  String FirstName;
    private  String LastName;
    @PrimaryKey
    private  String Email;
    private  String Password;
    private  String MobileNum;
    private  String Gender;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getMobileNum() {
        return MobileNum;
    }

    public void setMobileNum(String MobileNum) {
        this.MobileNum = MobileNum;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    @Override
    public String toString() {
        return "User{" +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", MobileNum='" + MobileNum + '\'' +
                ", Gender='" + Gender + '\'' +
                '}';
    }
}
