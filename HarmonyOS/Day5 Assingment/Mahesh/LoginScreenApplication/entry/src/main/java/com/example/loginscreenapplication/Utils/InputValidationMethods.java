package com.example.loginscreenapplication.Utils;

import ohos.agp.components.Component;
import ohos.agp.components.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationMethods {

    static final Pattern namePattern = Pattern.compile("[^a-zA-Z]");
    static final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+");
    static final Pattern nonNumericPattern = Pattern.compile("[^0-9]");

    public static void checkForNameError(String inputName, Text targetErrorText)
    {
        Matcher matcher = namePattern.matcher(inputName);
        boolean isValidName = !matcher.find();
        if (!isValidName)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
    public static void checkForEmailError(String inputEmail, Text targetErrorText)
    {
        if (!isValidEmail(inputEmail))
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }

    public static boolean isValidEmail(String inputEmail) {
        Matcher matcher = emailPattern.matcher(inputEmail);
        return matcher.matches();
    }

    public static void checkForPasswordError(String inputPassword, Text targetErrorText)
    {
        if (!isValidPassword(inputPassword))
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }

    public static boolean isValidPassword(String inputPassword) {
        return inputPassword.length() >= 8;
    }

    public static void checkForMobileError(String inputMobile, Text targetErrorText)
    {
        Matcher matcher = nonNumericPattern.matcher(inputMobile);
        boolean isValidMobile = (!matcher.find())&&(inputMobile.length() >= 1);
        if (!isValidMobile)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
}
