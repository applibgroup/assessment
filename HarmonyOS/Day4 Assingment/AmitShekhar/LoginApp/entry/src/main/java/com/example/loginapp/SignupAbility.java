package com.example.loginapp;

import com.example.loginapp.slice.SignupAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SignupAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SignupAbilitySlice.class.getName());
    }
}
