package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.LoginAbility;
import com.devpatel10.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.element.Element;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        //Image image=(Image)findComponentById(ResourceTable.Id_image);
       // image.setPixelMap(ResourceTable.Media_huawei_logo);

        Button login=(Button)findComponentById(ResourceTable.Id_login);
        //Button signUp=(Button) findComponentById(ResourceTable.Id_signup);
        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginAbilitySlice(),new Intent());
            }
        });
//        signUp.setClickedListener(new Component.ClickedListener() {
//            @Override
//            public void onClick(Component component) {
//                present(new SignUpAbilitySlice(),new Intent());
//            }
//        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
