package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.element.FrameAnimationElement;

public class MainAbilitySlice extends AbilitySlice {

    private AnimatorProperty animatorPropertySignup;
    private AnimatorProperty animatorPropertyLogin;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Component component = findComponentById(ResourceTable.Id_display_animation);
        FrameAnimationElement frameAnimationElement = new FrameAnimationElement(getContext(), ResourceTable.Graphic_animation_element);
        component.setBackground(frameAnimationElement);
        frameAnimationElement.start();

        Button btn_signup = (Button) findComponentById(ResourceTable.Id_btn_signup);
        Button btn_login = (Button) findComponentById(ResourceTable.Id_btn_login);

        animatorPropertySignup = btn_signup.createAnimatorProperty();
        animatorPropertyLogin = btn_login.createAnimatorProperty();
        animatorPropertySignup.scaleX((float) 0.80).scaleY((float) 0.80).setDuration(1000).setDelay(500).setLoopedCount(0);
        animatorPropertyLogin.scaleX((float) 0.80).scaleY((float) 0.80).setDuration(1000).setDelay(500).setLoopedCount(0);
        btn_signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                animatorPropertySignup.start();
            }
        });

        btn_login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                animatorPropertyLogin.start();
            }
        });

        setAnimatorListeners();
    }

    public void setAnimatorListeners(){
        animatorPropertySignup.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) { }

            @Override
            public void onStop(Animator animator) {
                animatorPropertySignup.reset();
                present(new SignupAbilitySlice(),new Intent());
            }

            @Override
            public void onCancel(Animator animator) { }
            @Override
            public void onEnd(Animator animator) { }
            @Override
            public void onPause(Animator animator) { }
            @Override
            public void onResume(Animator animator) { }
        });

        animatorPropertyLogin.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) { }

            @Override
            public void onStop(Animator animator) {
                animatorPropertyLogin.reset();
                present(new LoginAbilitySlice(),new Intent());
            }

            @Override
            public void onCancel(Animator animator) { }
            @Override
            public void onEnd(Animator animator) { }
            @Override
            public void onPause(Animator animator) { }
            @Override
            public void onResume(Animator animator) { }
        });
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
