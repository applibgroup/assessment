package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorScatter;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button login_btn = (Button) findComponentById(ResourceTable.Id_login);
        Button signup_btn = (Button) findComponentById(ResourceTable.Id_signup);
        Image image = (Image) findComponentById(ResourceTable.Id_logo);
        AnimatorProperty animatorProperty = image.createAnimatorProperty();
        animatorProperty.moveFromX(50).moveToX(1000).rotate(90).alpha(0).setDuration(2500).setDelay(500).setLoopedCount(-1);

        image.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                animatorProperty.stop();
            }});

        login_btn.setClickedListener(listener -> present(new LoginAbilitySlice(), new Intent()));
        signup_btn.setClickedListener(listener -> present(new SignupAbilitySlice(), new Intent()));
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
