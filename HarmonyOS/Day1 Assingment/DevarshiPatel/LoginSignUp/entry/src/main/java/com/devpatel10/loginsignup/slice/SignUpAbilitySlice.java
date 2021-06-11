package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class SignUpAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}