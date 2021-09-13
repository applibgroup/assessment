package com.example.day1.slice;

import com.example.day1.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image ImgView = (Image)findComponentById(ResourceTable.Id_img);
        Button btn = (Button)findComponentById(ResourceTable.Id_signupBtn);
        btn.setClickedListener(list -> present(new SignUpAbilitySlice(), new Intent()));
        Button loginbtn = (Button)findComponentById(ResourceTable.Id_loginBtn);
        loginbtn.setClickedListener(list -> present(new LoginAbilitySlice(), new Intent()));

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
