package com.example.userlogin.slice;

import com.example.userlogin.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    Button login,signup;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        login=(Button)findComponentById(ResourceTable.Id_main_login_button);
        signup=(Button)findComponentById(ResourceTable.Id_main_signup_button);
        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginSlice(),new Intent());
            }
        });
        signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignupSlice(),new Intent());
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
