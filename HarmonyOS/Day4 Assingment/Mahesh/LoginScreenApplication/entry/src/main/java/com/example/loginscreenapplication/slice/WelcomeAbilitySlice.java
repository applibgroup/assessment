package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.Model.UserData;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class WelcomeAbilitySlice extends AbilitySlice {
    public static final String USER_DATA = "UserData";
    public static final String WELCOME_BASE_TEXT = "Welcome, %s.";

    public static final String MALE_GENDERED_TITLE = "Mr.";
    public static final String FEMALE_GENDERED_TITLE = "Mrs.";

    Text welcomeText;

    UserData userData;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_welcome_layout);
        userData = intent.getSerializableParam(USER_DATA);

        welcomeText = (Text)findComponentById(ResourceTable.Id_welcomePageText);

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
        String completedWelcomeText = String.format(WELCOME_BASE_TEXT, fullName);

        welcomeText.setText(completedWelcomeText);
        }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

}
