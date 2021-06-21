package com.example.loginsignupapp.slice;

import com.example.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;


public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Image image = (Image) findComponentById(ResourceTable.Id_app_icon);

        image.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                helper(image);
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {

            }});

        image.setClickedListener(component -> helper(image));

        Button signup_btn = (Button)findComponentById(ResourceTable.Id_signup_btn);
        signup_btn.setClickedListener(list -> present(new SignupAbilitySlice(), new Intent()));

        Button login_btn = (Button)findComponentById(ResourceTable.Id_login_btn);
        login_btn.setClickedListener(list -> present(new LoginAbilitySlice(), new Intent()));
    }

    public void helper(Image image){
        AnimatorProperty animatorProperty = image.createAnimatorProperty();
        animatorProperty.rotate(360).alpha(0).setDuration(2500);
        animatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {

            }

            @Override
            public void onStop(Animator animator) {

            }

            @Override
            public void onCancel(Animator animator) {

            }

            @Override
            public void onEnd(Animator animator) {
                if (animatorProperty.getTarget().getAlpha() == 0){
                    animatorProperty.rotate(0).alpha(1).setDuration(2500).start();
                }
            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });
        animatorProperty.start();
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
