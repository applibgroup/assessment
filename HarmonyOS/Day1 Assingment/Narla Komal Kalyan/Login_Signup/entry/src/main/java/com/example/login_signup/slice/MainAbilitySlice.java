package com.example.login_signup.slice;

import com.example.login_signup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.OnClickListener;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button login = (Button)findComponentById(ResourceTable.Id_login_button);
        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginAbilitySlice(),new Intent());
            }
        });

        Button signup = (Button)findComponentById(ResourceTable.Id_signup_button);
        signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignupAbilitySlice(),new Intent());
            }
        });
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
