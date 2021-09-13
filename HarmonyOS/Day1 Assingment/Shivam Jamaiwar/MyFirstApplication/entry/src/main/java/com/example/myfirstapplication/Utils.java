package com.example.myfirstapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * check entered mail id is correct or not
     *
     * @param email entered mail id
     * @return boolean value, if entered mail id is correct return true otherwise return false
     */
    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * check entered contact no is correct or not
     *
     * @param contact entered contact no.
     * @return
     */
    public static boolean checkMobileNo(String contact) {
        if (contact == null) {
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[0-9]{10}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(contact);
        return matcher.matches();
    }
}
