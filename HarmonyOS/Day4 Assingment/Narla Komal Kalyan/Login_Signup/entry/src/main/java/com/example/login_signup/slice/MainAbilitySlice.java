package com.example.login_signup.slice;

import com.example.login_signup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    private AnimatorProperty img;
    private AnimatorProperty login_animation;
    private AnimatorProperty signup_animation;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Image image = (Image) findComponentById(ResourceTable.Id_image);
        img = image.createAnimatorProperty();
        img.rotate(360).setDuration(500).setDelay(50).setLoopedCount(10);


        Button login = (Button)findComponentById(ResourceTable.Id_login_button);
        login_animation = login.createAnimatorProperty();
        login_animation.scaleX((float) 0.10).scaleY((float) 0.10).setDuration(2000).setDelay(1000).setLoopedCount(0);
        login.setClickedListener(component -> login_animation.start());

        login_animation.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {
                img.start();
            }

            @Override
            public void onStop(Animator animator) {
                login_animation.reset();
                img.reset();
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

        Button signup = (Button)findComponentById(ResourceTable.Id_signup_button);
        signup_animation = signup.createAnimatorProperty();
        signup_animation.scaleX((float) 0.10).scaleY((float) 0.10).setDuration(2000).setDelay(1000).setLoopedCount(0);
        signup.setClickedListener(component -> signup_animation.start());

        signup_animation.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {
                img.start();
            }

            @Override
            public void onStop(Animator animator) {
                signup_animation.reset();
                img.reset();
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
