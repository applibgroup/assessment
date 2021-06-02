package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button login = (Button) findComponentById(ResourceTable.Id_login_btn);
        Button signup = (Button) findComponentById(ResourceTable.Id_signup_btn);
        login.setClickedListener(List -> present(new login(),new Intent()));
        signup.setClickedListener(List -> present(new signup(),new Intent()));
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
