package com.devpatel10.loginsignup;

import com.devpatel10.loginsignup.slice.LoginAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class LoginAbility extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(LoginAbilitySlice.class.getName());
    }
}
