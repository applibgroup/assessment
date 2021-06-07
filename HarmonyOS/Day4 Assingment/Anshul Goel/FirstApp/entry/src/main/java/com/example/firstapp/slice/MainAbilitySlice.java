package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {

    private AnimatorProperty btn_img_anim;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Image imgview= (Image) findComponentById(ResourceTable.Id_imgview);
        btn_img_anim= imgview.createAnimatorProperty();         //for animation
        btn_img_anim.setDuration(5000).moveFromX(-230).moveToX(250).rotate(360).setLoopedCount(-1).setCurveType(Animator.CurveType.ACCELERATE);

        imgview.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                btn_img_anim.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                btn_img_anim.stop();
            }});

        Button button_login= (Button) findComponentById(ResourceTable.Id_button_login);
        button_login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

               present(new LoginAbilitySlice(),new Intent());

            }
        });

        Button button_signup= (Button) findComponentById(ResourceTable.Id_button_signup);
        button_signup.setClickedListener(list -> present(new SignupAbilitySlice(),new Intent()));

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