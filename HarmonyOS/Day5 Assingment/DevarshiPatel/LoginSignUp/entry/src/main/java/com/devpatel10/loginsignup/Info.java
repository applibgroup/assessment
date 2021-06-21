package com.devpatel10.loginsignup;

public class Info {
    public static String EMAIL="email";
    public static String PASSWORD="password";
    public static String FIRST_NAME="fname";
    public static String LAST_NAME="lname";

    public static String getFirstName() {
        return FIRST_NAME;
    }

    public static void setFirstName(String firstName) {
        FIRST_NAME = firstName;
    }

    public static String getLastName() {
        return LAST_NAME;
    }

    public static void setLastName(String lastName) {
        LAST_NAME = lastName;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static void setEMAIL(String EMAIL) {
        Info.EMAIL = EMAIL;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Info.PASSWORD = PASSWORD;
    }
}
