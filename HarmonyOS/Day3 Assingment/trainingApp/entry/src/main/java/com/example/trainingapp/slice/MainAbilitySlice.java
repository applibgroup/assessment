package com.example.trainingapp.slice;

import com.example.trainingapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button bt1 = (Button) findComponentById(ResourceTable.Id_log);
        Button bt2 = (Button) findComponentById(ResourceTable.Id_sign);

        bt1.setClickedListener(listener -> present(new LoginSlice(), new Intent()));
        bt2.setClickedListener(listener -> present(new SignupSlice(), new Intent()));
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
