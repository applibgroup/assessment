package com.example.loginapp;

import com.example.loginapp.slice.LoginAbilitySlice;
import com.example.loginapp.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DeviceConfigInfo;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.system.DeviceInfo;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

//        bt.setClickedListener(listener -> {
//            Operation operation = new Intent.OperationBuilder()
//                    .withDeviceId("")
//                    .withBundleName("com.example.loginapp")
//                    .withAbilityName("com.example.loginapp.LoginAbility")
//                    .build();
//
//            Intent intent2 = new Intent();
//            intent2.setOperation(operation);
//            startAbility(intent2);
//        });
    }
}
