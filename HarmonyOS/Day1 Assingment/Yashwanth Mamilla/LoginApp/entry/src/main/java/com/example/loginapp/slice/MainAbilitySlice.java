package com.example.loginapp.slice;

import com.example.loginapp.Login;
import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {

    Button login;
    Button signup;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        login= (Button) findComponentById(ResourceTable.Id_login);
        signup=(Button) findComponentById(ResourceTable.Id_signup_button);
        login.setClickedListener(component -> present(new LoginSlice(),new Intent()));
        signup.setClickedListener(component -> present(new signupSlice(),new Intent()));
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
