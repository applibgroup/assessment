package com.example.myfirstapplication.slice;

import com.example.myfirstapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {

    private Button mBtnLogin, mBtnSignup;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        mBtnLogin = (Button) findComponentById(ResourceTable.Id_btn_login);
        mBtnSignup = (Button) findComponentById(ResourceTable.Id_btn_signup);

        mBtnLogin.setClickedListener(listener -> present(new LoginAbilitySlice(), new Intent()));
        mBtnSignup.setClickedListener(listener -> present(new SignupAbilitySlice(), new Intent()));
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
