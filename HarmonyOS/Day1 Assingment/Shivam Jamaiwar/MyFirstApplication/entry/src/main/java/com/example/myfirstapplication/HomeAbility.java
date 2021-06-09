package com.example.myfirstapplication;

import com.example.myfirstapplication.slice.HomeAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class HomeAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(HomeAbilitySlice.class.getName());
    }
}
