package com.applib.loginsignupapp.slice;

import com.applib.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


public class MainAbilitySlice extends AbilitySlice {

    private AnimatorValue imageAnimation;
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0xD001100, "MAIN_LOG");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Image imageLogo = (Image) findComponentById(ResourceTable.Id_image_main_slice);
        Button loginButton = (Button) findComponentById(ResourceTable.Id_button_login_main_slice);
        Button signUpButton = (Button) findComponentById(ResourceTable.Id_button_signup_main_slice);

        imageAnimation = new AnimatorValue();
        imageAnimation.setDuration(2000);
        imageAnimation.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
        imageAnimation.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v) {
                imageLogo.setContentPosition(imageLogo.getContentPositionX(),v*500);
            }
        });
        imageAnimation.start();

        loginButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginSlice(),new Intent());
            }
        });

        signUpButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignUpSlice(), new Intent());
            }
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
