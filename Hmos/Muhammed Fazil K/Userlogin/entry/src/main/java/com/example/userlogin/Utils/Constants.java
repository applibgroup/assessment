package com.example.userlogin.Utils;

public class Constants {
    public static final String FIRSTNAMEPATTERN = "[A-Z][a-z]*";
    public static final String LASTNAMEPATTERN = "[A-Z][a-z]*";
    public static final String EMAILPATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String MOBILEPATTERN = "^\\d{10}$";

    public static final String DB_NAME="UserDetailsDB.db";
    public static String DB_ALIAS="UserdetailsDB.db";
    public static final String FIRSTNAME_COLUMN="firstName";
    public static final String LASTNAME_COLUMN="lastname";
    public static final String EMAIL_COLUMN="email";
    public static final String PASSWORD_COLUMN="password";
    public static final String MOBILE_COLUMN="mobile";
    public static final String GENDER_COLUMN="gender";



}
