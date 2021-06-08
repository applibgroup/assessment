package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.Model.UserData;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

import static com.example.loginscreenapplication.Utils.UserDataHelperMethods.getFullName;

public class WelcomeAbilitySlice extends AbilitySlice {
    public static final String USER_DATA = "UserData";
    public static final String WELCOME_BASE_TEXT = "Welcome, %s.";

    Text welcomeText;

    UserData userData;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_welcome_layout);
        userData = intent.getSerializableParam(USER_DATA);

        welcomeText = (Text)findComponentById(ResourceTable.Id_welcomePageText);

        String fullName = getFullName(userData);
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
