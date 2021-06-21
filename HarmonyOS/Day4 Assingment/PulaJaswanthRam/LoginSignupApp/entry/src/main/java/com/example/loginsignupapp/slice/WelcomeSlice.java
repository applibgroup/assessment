package com.example.loginsignupapp.slice;

import com.example.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class WelcomeSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_welcome);
        String fName = intent.getStringParam("fName");
        Text text = (Text) findComponentById(ResourceTable.Id_welcome_txt);
        text.setText("Welcome "+fName+" !!");
    }
}
