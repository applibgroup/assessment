package com.example.trainingapp.slice;

import com.example.trainingapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button bt1 = (Button) findComponentById(ResourceTable.Id_log);
        Button bt2 = (Button) findComponentById(ResourceTable.Id_sign);

        Image img = (Image) findComponentById(ResourceTable.Id_icon);
        AnimatorProperty animatorProperty = img.createAnimatorProperty();
        animatorProperty.moveFromX(50).moveToX(50).rotate(180).alpha(0).setDuration(2500).setDelay(500).setLoopedCount(-1);

        img.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                animatorProperty.stop();
            }
        });

        bt1.setClickedListener(listener -> present(new LoginSlice(), new Intent()));
        bt2.setClickedListener(listener -> present(new SignupSlice(), new Intent()));
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
