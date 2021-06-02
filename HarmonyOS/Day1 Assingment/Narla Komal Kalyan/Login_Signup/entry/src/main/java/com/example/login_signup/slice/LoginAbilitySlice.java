package com.example.login_signup.slice;

import com.example.login_signup.ResourceTable;
        import ohos.aafwk.ability.AbilitySlice;
        import ohos.aafwk.content.Intent;

public class LoginAbilitySlice extends AbilitySlice {

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
    }
}