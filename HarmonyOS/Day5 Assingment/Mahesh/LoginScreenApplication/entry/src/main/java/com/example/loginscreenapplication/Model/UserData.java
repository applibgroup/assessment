package com.example.loginscreenapplication.Model;


import ohos.data.rdb.ValuesBucket;

import java.io.Serializable;

public class UserData implements Serializable {

    public static final String EMAIL_FIELD = "email";
    public static final String LAST_NAME_FIELD = "lastName";
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String PASSWORD_FIELD = "password";
    public static final String MOBILE_FIELD = "mobile";
    public static final String GENDER_FIELD = "gender";
    String firstName;
    String lastName;
    String email;
    String password;
    String mobile;
    String gender;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Gender getGender()
    {
        try{
            return Gender.valueOf(gender);
        }
        catch (IllegalArgumentException e)
        {
            // Default value
            return Gender.Male;
        }
    }

    public void setGender(Gender gender) {
        this.gender = gender.toString();
    }

    public enum Gender {
        Male,
        Female
    }

    public ValuesBucket createValuesBucketFromData()
    {
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putString(FIRST_NAME_FIELD,firstName);
        valuesBucket.putString(LAST_NAME_FIELD,lastName);
        valuesBucket.putString(EMAIL_FIELD,email);
        valuesBucket.putString(PASSWORD_FIELD,password);
        valuesBucket.putString(MOBILE_FIELD,mobile);
        valuesBucket.putString(GENDER_FIELD,gender);
        return valuesBucket;
    }
}