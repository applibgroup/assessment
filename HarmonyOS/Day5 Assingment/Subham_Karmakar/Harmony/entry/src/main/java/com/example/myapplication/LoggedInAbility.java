package com.example.myapplication;

import com.example.myapplication.slice.LoggedInAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class LoggedInAbility extends Ability
{
    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setMainRoute(LoggedInAbilitySlice.class.getName());
    }
}
