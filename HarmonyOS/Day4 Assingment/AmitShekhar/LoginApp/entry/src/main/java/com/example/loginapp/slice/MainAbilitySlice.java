package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button login_bt = (Button)findComponentById(ResourceTable.Id_login_btn);
        Button signup_bt = (Button)findComponentById(ResourceTable.Id_signup_btn);
        login_bt.setClickedListener(listener -> present(new LoginAbilitySlice(), new Intent()));
        signup_bt.setClickedListener(listener -> present(new SignupAbilitySlice(), new Intent()));

        Image image = (Image)findComponentById(ResourceTable.Id_logo);
        AnimatorProperty imageAnimation = image.createAnimatorProperty();
        imageAnimation.setDuration(2000).rotate(360).setLoopedCount(1);
        image.setClickedListener(component -> imageAnimation.start());
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
