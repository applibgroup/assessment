package com.example.loginapp;

import com.example.loginapp.slice.signupSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class signup extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(signupSlice.class.getName());
    }
}
