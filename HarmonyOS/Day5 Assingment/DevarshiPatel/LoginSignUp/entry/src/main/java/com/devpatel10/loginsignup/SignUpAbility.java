package com.devpatel10.loginsignup;

import com.devpatel10.loginsignup.slice.SignUpAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SignUpAbility extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SignUpAbilitySlice.class.getName());
    }
}
