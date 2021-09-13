package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class Dashboard extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_dashboard);
        Button logout = (Button) findComponentById(ResourceTable.Id_logout_btn);
        logout.setClickedListener(List -> present(new MainAbilitySlice(),new Intent()));
    }
}
