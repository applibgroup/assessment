package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button login_btn = (Button) findComponentById(ResourceTable.Id_login);
        Button signup_btn = (Button) findComponentById(ResourceTable.Id_signup);

        login_btn.setClickedListener(listener -> present(new LoginAbilitySlice(), new Intent()));
        signup_btn.setClickedListener(listener -> present(new SignupAbilitySlice(), new Intent()));
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
