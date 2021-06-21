package com.example.myapplication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckingDataUtils {

    public static Boolean checkName(String Name){ return Pattern.matches("[a-zA-Z]+",Name); }

    public static Boolean checkPassword(String string){
        return string.length() >= 5;
    }

    public static Boolean checkEmail(String string){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(string);
        return matcher.find();
    }

    public static Boolean checkMobile(String string){
        try{
            Integer.parseInt(string);
            return true;
        }
        catch (Exception E){
            return false;
        }
    }
}
