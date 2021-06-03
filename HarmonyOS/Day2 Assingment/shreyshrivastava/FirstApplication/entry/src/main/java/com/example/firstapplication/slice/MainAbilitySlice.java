package com.example.firstapplication.slice;


import com.example.firstapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    Button button_signup;
    Button button_login;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        button_signup = (Button)findComponentById(ResourceTable.Id_button_main_signup);
        button_signup.setClickedListener(component -> present(new SignupAbilitySlice(), new Intent()));

        button_login = (Button)findComponentById(ResourceTable.Id_button_main_login);
        button_login.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
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
