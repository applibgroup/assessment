package com.example.trainingapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.trainingapp.ResourceTable;

public class LoginSuccessSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login_success);
    }
}
