package com.applib.loginsignupapp.slice;

import com.applib.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;


public class MainAbilitySlice extends AbilitySlice {


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button loginButton = (Button) findComponentById(ResourceTable.Id_button_login_main_slice);
        Button signUpButton = (Button) findComponentById(ResourceTable.Id_button_signup_main_slice);

        loginButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginSlice(),new Intent());
            }
        });

        signUpButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignUpSlice(), new Intent());
            }
        });
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
