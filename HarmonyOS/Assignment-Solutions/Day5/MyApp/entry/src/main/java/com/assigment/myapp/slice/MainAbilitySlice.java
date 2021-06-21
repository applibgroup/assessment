package com.assigment.myapp.slice;

import com.assigment.myapp.ResourceTable;
import com.assigment.myapp.Utils.AnimationUtils;
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
        setListeners();
    }

    private void initViews() {
        appLogo = (Image) findComponentById(ResourceTable.Id_image);
        login = (Button) findComponentById(ResourceTable.Id_login);
        signup = (Button) findComponentById(ResourceTable.Id_signup);
        appLogo.setPixelMap(ResourceTable.Media_my_app);
        appLogo.setScaleMode(Image.ScaleMode.STRETCH);
    }

    private void setListeners() {
        appLogo.setClickedListener(component -> AnimationUtils.setAppLogoAnimation(appLogo));
        login.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
        signup.setClickedListener(component -> present(new SignUpAbilitySlice(), new Intent()));
        appLogo.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                AnimationUtils.setAppLogoAnimation(appLogo);
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
