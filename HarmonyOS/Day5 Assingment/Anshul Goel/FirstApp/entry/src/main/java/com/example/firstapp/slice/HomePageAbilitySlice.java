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
        int x1=8000,x2=80,x3=590,x4=1,x5=-1;

        Text welcome=(Text) findComponentById(ResourceTable.Id_welcome);
        button2Animation = welcome.createAnimatorProperty();  //0r button2Animation.setTarget(targetButton2)
        button2Animation.setDuration(x1).moveFromX(x2).moveToX(x3).alpha(x4).setLoopedCount(x5).setCurveType(Animator.CurveType.CYCLE);
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