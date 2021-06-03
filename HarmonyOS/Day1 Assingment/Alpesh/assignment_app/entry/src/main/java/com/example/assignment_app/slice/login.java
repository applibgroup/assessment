package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class login extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);
    }
}
