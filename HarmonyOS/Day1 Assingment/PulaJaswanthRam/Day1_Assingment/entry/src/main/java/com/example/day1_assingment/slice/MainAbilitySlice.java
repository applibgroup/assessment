package com.example.day1_assingment.slice;

import com.example.day1_assingment.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button signup_btn = (Button)findComponentById(ResourceTable.Id_signup_btn);
        signup_btn.setClickedListener(list -> present(new SignupAbilitySlice(), new Intent()));

        Button login_btn = (Button)findComponentById(ResourceTable.Id_login_btn);
        login_btn.setClickedListener(list -> present(new LoginAbilitySlice(), new Intent()));
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
