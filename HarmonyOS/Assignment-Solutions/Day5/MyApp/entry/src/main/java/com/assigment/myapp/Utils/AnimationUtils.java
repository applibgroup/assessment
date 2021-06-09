package com.assigment.myapp.Utils;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Image;

public class AnimationUtils {
    private static float ZERO_FLOAT = 0.0f;
    private static float ONE_FLOAT = 1.0f;
    private static long ANIM_DURATION = 700;

    public static void setAppLogoAnimation(Image appLogo){
        {
            AnimatorProperty animatorProperty = appLogo.createAnimatorProperty().setCurveType(Animator.CurveType.CUBIC_BEZIER_EXTREME_DECELERATION)
                    .scaleXFrom(ONE_FLOAT).scaleX(ZERO_FLOAT).scaleYFrom(ONE_FLOAT).scaleY(ZERO_FLOAT).setDuration(ANIM_DURATION);
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
                    if (animatorProperty.getTarget().getScaleX() == ZERO_FLOAT
                            && animatorProperty.getTarget().getScaleY() == ZERO_FLOAT) {
                        animatorProperty.setCurveType(Animator.CurveType.OVERSHOOT)
                                .scaleXFrom(ZERO_FLOAT).scaleX(ONE_FLOAT).scaleYFrom(ZERO_FLOAT).scaleY(ONE_FLOAT).setDuration(ANIM_DURATION).start();
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
    }
}
