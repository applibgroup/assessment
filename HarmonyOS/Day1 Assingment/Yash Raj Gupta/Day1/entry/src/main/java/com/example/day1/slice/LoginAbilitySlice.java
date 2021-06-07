package com.example.day1.slice;

import com.example.day1.ResourceTable;
import ohos.aafwk.content.Intent;

public class LoginAbilitySlice extends MainAbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
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
