package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class LoginAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_login);
    }
}
