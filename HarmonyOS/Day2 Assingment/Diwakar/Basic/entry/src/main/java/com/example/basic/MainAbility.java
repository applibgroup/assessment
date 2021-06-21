package com.example.basic;

import com.example.basic.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbility extends Ability {

    public Button login_btn, signUp_btn;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());



//        login_btn.setClickedListener(new Component.ClickedListener() {
//            @Override
//            public void onClick(Component component) {
////                Intent intent1 = new Intent();
////                Operation operation = new Intent.OperationBuilder()
////                        .withDeviceId("")
////                        .withBundleName("com.example.basic")
////                        .withAbilityName("com.example.basic.Login")
////                        .build();
////                intent1.setOperation(operation);
////                startAbility(intent1);
//            }
//        });
    }
}
