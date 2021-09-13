package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Text;

public class WelcomePageAbilitySlice extends AbilitySlice {
    private AnimatorProperty button2Animation;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_home);
        Text welcometext = (Text)findComponentById(ResourceTable.Id_WelcomeText);
        String fullname = intent.getStringParam("flname");
        welcometext.setText("Welcome " + fullname);
        button2Animation = welcometext.createAnimatorProperty();  //0r button2Animation.setTarget(targetButton2)
        button2Animation.setDuration(2000).moveFromX(50).moveToX(750).rotate(90).alpha(1).setLoopedCount(-1);
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
