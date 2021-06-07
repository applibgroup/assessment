package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    Button loginButton;
    Button registerButton;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        loginButton = (Button)findComponentById(ResourceTable.Id_loginButton);
        registerButton = (Button)findComponentById(ResourceTable.Id_registerButton);

        loginButton.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
        registerButton.setClickedListener(component -> present(new RegisterAbilitySlice(), new Intent()));
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
