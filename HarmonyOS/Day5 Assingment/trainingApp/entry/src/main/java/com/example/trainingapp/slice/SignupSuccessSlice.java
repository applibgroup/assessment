package com.example.trainingapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.trainingapp.ResourceTable;

public class SignupSuccessSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup_success);
    }
}
