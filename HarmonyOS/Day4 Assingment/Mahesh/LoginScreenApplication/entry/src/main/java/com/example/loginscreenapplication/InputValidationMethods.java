package com.example.loginscreenapplication;

import ohos.agp.components.Component;
import ohos.agp.components.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationMethods {

    public static void checkForNameError(String inputName, Text targetErrorText)
    {
        Pattern namePattern = Pattern.compile("[^a-zA-Z]");
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

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+");
        Matcher matcher = emailPattern.matcher(inputEmail);
        return matcher.find();
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
        Pattern nonNumericPattern = Pattern.compile("[^0-9]");
        Matcher matcher = nonNumericPattern.matcher(inputMobile);
        boolean isValidMobile = (!matcher.find())&&(inputMobile.length() >= 1);
        if (!isValidMobile)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
}
