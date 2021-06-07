package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

public class HomePageAbilitySlice extends AbilitySlice {

    AnimatorProperty button2Animation;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_homepage);

        Text welcome=(Text) findComponentById(ResourceTable.Id_welcome);
        button2Animation = welcome.createAnimatorProperty();  //0r button2Animation.setTarget(targetButton2)
        button2Animation.setDuration(8000).moveFromX(80).moveToX(590).alpha(1).setLoopedCount(-1).setCurveType(Animator.CurveType.CYCLE);
        button2Animation.start();

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