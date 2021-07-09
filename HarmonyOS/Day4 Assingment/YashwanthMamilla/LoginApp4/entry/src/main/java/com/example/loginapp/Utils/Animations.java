package com.example.loginapp.Utils;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Image;

public class Animations {
    private static float p1 = 0.0f;
    private static float p2 = 1.2f;
    private static long ANIM_DURATION = 500;

    public static void setLogoAnimation(Image appLogo){
        AnimatorProperty animatorProperty= appLogo.createAnimatorProperty();
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
                if (animatorProperty.getTarget().getScaleX() == p1
                        && animatorProperty.getTarget().getScaleY() == p1) {
                    animatorProperty.setCurveType(Animator.CurveType.BOUNCE)
                            .scaleXFrom(p1).scaleX(p2).scaleYFrom(p1).scaleY(p2).setDuration(ANIM_DURATION);
                    animatorProperty.start();
                }
            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });
        animatorProperty.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE)
                .scaleXFrom(p2).scaleX(p1).scaleYFrom(p2).scaleY(p1).setDuration(ANIM_DURATION).start();

    }
}
