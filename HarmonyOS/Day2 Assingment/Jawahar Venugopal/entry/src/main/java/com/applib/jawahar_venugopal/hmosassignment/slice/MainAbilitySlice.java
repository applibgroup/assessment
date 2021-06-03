package com.applib.jawahar_venugopal.hmosassignment.slice;

import com.applib.jawahar_venugopal.hmosassignment.ResourceTable;
import com.applib.jawahar_venugopal.hmosassignment.customview.CustomButton;
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
        CustomButton signupButton = (CustomButton) findComponentById(ResourceTable.Id_signup_button);
        CustomButton loginButton = (CustomButton) findComponentById(ResourceTable.Id_login_button);
        signupButton.setClickedListener(view -> present(new SignupAbilitySlice(), new Intent()));
        loginButton.setClickedListener(view -> present(new LoginAbilitySlice(), new Intent()));
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
