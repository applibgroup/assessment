package com.example.loginscreenapplication.Utils;

import com.example.loginscreenapplication.Model.UserData;

public class UserDataHelperMethods {

    public static final String MALE_GENDERED_TITLE = "Mr.";
    public static final String FEMALE_GENDERED_TITLE = "Mrs.";

    public static String getFullName(UserData userData) {
        String prefix = "";
        String firstName = userData.getFirstName();
        String lastName = userData.getLastName();
        UserData.Gender gender = userData.getGender();

        if (gender == UserData.Gender.Female)
        {
            prefix = FEMALE_GENDERED_TITLE;
        }else{
            prefix = MALE_GENDERED_TITLE;
        }
        String fullName = String.format("%s %s %s", prefix, firstName, lastName);
        return fullName;
    }
}
