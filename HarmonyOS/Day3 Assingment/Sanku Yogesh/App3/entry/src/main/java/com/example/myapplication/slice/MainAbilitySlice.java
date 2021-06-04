package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button btn_signup = (Button) findComponentById(ResourceTable.Id_btn_signup);
        Button btn_login = (Button) findComponentById(ResourceTable.Id_btn_login);

        btn_signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignupAbilitySlice(),new Intent());
            }
        });

        btn_login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginAbilitySlice(),new Intent());
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
