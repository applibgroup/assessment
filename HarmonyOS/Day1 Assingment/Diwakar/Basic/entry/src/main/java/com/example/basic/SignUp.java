package com.example.basic;

import com.example.basic.slice.SignUpSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SignUp extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SignUpSlice.class.getName());
    }
}
