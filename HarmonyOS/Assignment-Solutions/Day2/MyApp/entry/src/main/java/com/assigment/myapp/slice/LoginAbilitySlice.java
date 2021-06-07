package com.assigment.myapp.slice;

import com.assigment.myapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class LoginAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_login_page);
    }
}
