package com.example.harmonytutorial.slice;

import com.example.harmonytutorial.ResourceTable;
import com.example.harmonytutorial.animation;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
public class MainAbilitySlice extends AbilitySlice {
    private Image appLogo;
    private Button login;
    private Button signup;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initViews();
        appLogo.setPixelMap(ResourceTable.Media_huawei_logo);
        appLogo.setScaleMode(Image.ScaleMode.STRETCH);
        setListeners();

    }

    private void initViews() {
        appLogo = (Image) findComponentById(ResourceTable.Id_image);
        login = (Button) findComponentById(ResourceTable.Id_login_button);
        signup = (Button) findComponentById(ResourceTable.Id_signup_button);
    }

    private void setListeners() {
        login.setClickedListener(component -> present(new LoginSlice(), new Intent()));
        signup.setClickedListener(component -> present(new SignUpSlice(), new Intent()));
        appLogo.setClickedListener(component -> animation.setAppLogoAnimation(appLogo));
        appLogo.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animation.setAppLogoAnimation(appLogo);
            }
            @Override
            public void onComponentUnboundFromWindow(Component component) {

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
