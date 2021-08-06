package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    static long ANIMATION_DURATION=1000;
    static float FROM_ANIMATION= 1.0F;
    static float TO_ANIMATION= 300.0F;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image image=(Image)findComponentById(ResourceTable.Id_image);
        image.setPixelMap(ResourceTable.Media_huawei_logo);
        image.setClickedListener(component -> AnimationLOGO(image));
        image.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                AnimationLOGO(image);
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {

            }
        });


        Button login=(Button)findComponentById(ResourceTable.Id_login);
        Button signUp=(Button) findComponentById(ResourceTable.Id_signup);
        login.setClickedListener(list->present(new LoginAbilitySlice(),new Intent()));
        signUp.setClickedListener(list->present(new SignUpAbilitySlice(),new Intent()));
    }

    @Override
    public void onActive() {
        super.onActive();
    }
    public void AnimationLOGO(Image image)
    {
        AnimatorProperty animatorProperty=image.createAnimatorProperty();
        animatorProperty.setCurveType(Animator.CurveType.CUBIC_HERMITE)
                .moveFromX(FROM_ANIMATION).moveToX(TO_ANIMATION)
                .moveFromY(FROM_ANIMATION).moveToY(TO_ANIMATION)
                .setDuration(ANIMATION_DURATION);
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
                if (animatorProperty.getTarget().getScaleX() == TO_ANIMATION
                        && animatorProperty.getTarget().getScaleY() == TO_ANIMATION)
                {
                    animatorProperty.setCurveType(Animator.CurveType.SPRING)
                            .moveToY(FROM_ANIMATION).moveToX(FROM_ANIMATION)
                            .setDuration(ANIMATION_DURATION);
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
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
