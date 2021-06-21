package com.assigment.myapp.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {
    private static final String PATTERN_NUMBER_REGEX = ".*\\d.*";
    private static final String PATTERN_SPECIAL_CHAR = "[^a-zA-Z0-9]";
    private static final String PATTERN_NUMBER = "[^0-9]";
    public static boolean isConsistOfNumber(String string) {
        return string.matches(PATTERN_NUMBER_REGEX);
    }

    /**
     * checks if the string contains a special character
     *
     * @param string - input string
     * @return - true if it contains, else return false
     */
    public static boolean isContainSpecialCharacter(String string) {
        Pattern pattern = Pattern.compile(PATTERN_SPECIAL_CHAR);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * checks if string contains only numbers
     *
     * @param string - input string
     * @return true if it contains only numbers, else return false
     */
    public static boolean isContainOnlyNumber(String string) {
        Pattern pattern = Pattern.compile(PATTERN_NUMBER);
        Matcher matcher = pattern.matcher(string);
        return !matcher.find();
    }
}
