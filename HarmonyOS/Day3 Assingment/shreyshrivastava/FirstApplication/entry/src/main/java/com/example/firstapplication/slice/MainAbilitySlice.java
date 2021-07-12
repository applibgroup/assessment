package com.example.firstapplication.slice;


import com.example.firstapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    Image image;

    Button button_signup;
    Button button_login;

    AnimatorProperty animatorProperty;

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        image = (Image) findComponentById(ResourceTable.Id_image_main);
        animatorProperty = image.createAnimatorProperty();
        animatorProperty.setDuration(5000).alpha(0).rotate(360).setLoopedCount(-1).setCurveType(Animator.CurveType.ACCELERATE);

        image.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                animatorProperty.stop();
            }});

        button_signup = (Button)findComponentById(ResourceTable.Id_button_main_signup);
        button_signup.setClickedListener(component -> present(new SignupAbilitySlice(), new Intent()));

        button_login = (Button)findComponentById(ResourceTable.Id_button_main_login);
        button_login.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
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
