package com.example.loginapp;

import com.example.loginapp.slice.DummyAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DummyAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DummyAbilitySlice.class.getName());
    }
}
