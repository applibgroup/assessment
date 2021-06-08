package com.example.basic;

import com.example.basic.slice.ProfileSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.window.dialog.ToastDialog;

public class Profile extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ProfileSlice.class.getName());


    }
}
