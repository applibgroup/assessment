package com.example.userlogin.slice;

import com.example.userlogin.ResourceTable;
import com.example.userlogin.Utils.Constants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class WelcomeSlice extends AbilitySlice {
    Text welcome;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_welcome_page);
        welcome=(Text)findComponentById(ResourceTable.Id_welcome_text);
        welcome.setText("Welcome "+intent.getStringParam(Constants.FIRSTNAME_COLUMN));

    }
}
