package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.timelinecurves.CubicBezierCurve;
import ohos.agp.components.Button;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    Button loginButton;
    Button registerButton;
    Image imageLogo;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        loginButton = (Button)findComponentById(ResourceTable.Id_loginButton);
        registerButton = (Button)findComponentById(ResourceTable.Id_registerButton);
        imageLogo = (Image)findComponentById(ResourceTable.Id_imageLogo);
        AnimatorProperty imageAnimatorProperty = createRotationAnimation(imageLogo);

        imageLogo.setClickedListener(component -> {
            imageAnimatorProperty.reset();
            imageAnimatorProperty.start();
        });
        loginButton.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
        registerButton.setClickedListener(component -> present(new RegisterAbilitySlice(), new Intent()));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private AnimatorProperty createRotationAnimation(Image targetImage)
    {
        AnimatorProperty animatorProperty = targetImage.createAnimatorProperty()
                .setCurveType(Animator.CurveType.ANTICIPATE_OVERSHOOT)
                .rotate(360f).setDuration(4000).setDelay(500);
        return animatorProperty;
    }
}
