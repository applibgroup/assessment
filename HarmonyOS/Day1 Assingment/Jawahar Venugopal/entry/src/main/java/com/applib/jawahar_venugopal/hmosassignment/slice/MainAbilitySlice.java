package com.applib.jawahar_venugopal.hmosassignment.slice;

import com.applib.jawahar_venugopal.hmosassignment.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initViews();
    }

    private void initViews() {
        Button signupButton = (Button) findComponentById(ResourceTable.Id_signup_button);
        Button loginButton = (Button) findComponentById(ResourceTable.Id_login_button);
        signupButton.setClickedListener(list -> present(new SignupAbilitySlice(), new Intent()));
        loginButton.setClickedListener(list -> present(new LoginAbilitySlice(), new Intent()));
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
