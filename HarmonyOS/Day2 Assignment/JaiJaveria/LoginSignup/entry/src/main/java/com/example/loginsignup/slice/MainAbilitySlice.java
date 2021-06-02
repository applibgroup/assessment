package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button login_button = (Button) findComponentById(ResourceTable.Id_xLoginB);
        Button signup_button = (Button) findComponentById(ResourceTable.Id_xSignUpB);
        login_button.setClickedListener(listener ->present(new LoginPage(), new Intent()));
        signup_button.setClickedListener(listener ->present(new SignupPage(), new Intent()));
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
