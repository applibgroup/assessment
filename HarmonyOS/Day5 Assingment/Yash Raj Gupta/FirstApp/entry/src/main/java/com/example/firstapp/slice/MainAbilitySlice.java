package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {
    private AnimatorProperty button1Animation;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image ImgView = (Image)findComponentById(ResourceTable.Id_img);
        button1Animation= ImgView.createAnimatorProperty();         //for animation
        button1Animation.setDuration(5000).moveFromX(50).moveToX(750).rotate(360).setLoopedCount(-1).setCurveType(Animator.CurveType.ACCELERATE);
        ImgView.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                button1Animation.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                button1Animation.stop();
            }});
        Button btn = (Button)findComponentById(ResourceTable.Id_signupBtn);
        btn.setClickedListener(list -> present(new SignUpAbilitySlice(), new Intent()));
        Button loginbtn = (Button)findComponentById(ResourceTable.Id_loginBtn);
        loginbtn.setClickedListener(list -> present(new LoginAbilitySlice(), new Intent()));

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
