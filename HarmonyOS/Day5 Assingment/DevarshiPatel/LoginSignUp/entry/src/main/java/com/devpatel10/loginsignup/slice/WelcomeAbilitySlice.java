package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.Info;
import com.devpatel10.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class WelcomeAbilitySlice extends AbilitySlice {
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_welcome);
        Text welcome=(Text)findComponentById(ResourceTable.Id_heading);
        if(intent !=null)
        {
            welcome.setText("Welcome "+intent.getStringParam(Info.FIRST_NAME)+" "+intent.getStringParam(Info.LAST_NAME)+"!");

        }

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
