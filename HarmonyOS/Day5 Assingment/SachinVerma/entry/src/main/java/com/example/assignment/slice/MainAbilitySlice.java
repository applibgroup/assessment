package com.example.assignment.slice;

import com.example.assignment.ResourceTable;
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
        Button s = (Button) findComponentById(ResourceTable.Id_signup);
        s.setClickedListener(listener -> present(new SignUpSlice(), new Intent()));

        Button l = (Button) findComponentById(ResourceTable.Id_login);
        l.setClickedListener(listener -> present(new LoginSlice(), new Intent()));

        Image img = (Image) findComponentById(ResourceTable.Id_img);
        AnimatorProperty animatorProperty = img.createAnimatorProperty();
        animatorProperty.moveFromY(50).moveToY(300).setDuration(2500).setDelay(200).setLoopedCount(0);

        img.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                animatorProperty.stop();
            }});
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
