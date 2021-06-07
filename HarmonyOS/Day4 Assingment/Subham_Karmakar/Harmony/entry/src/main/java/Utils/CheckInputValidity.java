package Utils;

import java.util.regex.Pattern;

public class CheckInputValidity
{
    public boolean checkFirstName(String s)
    {
        String regex = "[A-Z][a-zA-Z]*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public  boolean checkLastName(String s)
    {
        String regex ="[a-zA-z]+([ '-][a-zA-Z]+)*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public  boolean checkEmail(String s)
    {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public  boolean checkPassword(String s)
    {
        String regex = "^.{5,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public  boolean checkMobile(String s)
    {
        String regex = "\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }
}
