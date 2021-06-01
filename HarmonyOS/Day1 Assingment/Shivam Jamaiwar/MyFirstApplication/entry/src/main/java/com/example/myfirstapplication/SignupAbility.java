package com.example.myfirstapplication;

import com.example.myfirstapplication.slice.SignupAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SignupAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SignupAbilitySlice.class.getName());
    }
}
