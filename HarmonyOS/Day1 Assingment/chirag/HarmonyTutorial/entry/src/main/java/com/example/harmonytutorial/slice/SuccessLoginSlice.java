package com.example.harmonytutorial.slice;

import com.example.harmonytutorial.ResourceTable;
import com.example.harmonytutorial.animation;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;

public class SuccessLoginSlice extends AbilitySlice {
    private Image welcomeImage;
    private Text welcomeText;
    @Override
    public void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_success_login);
        welcomeImage = (Image) findComponentById(ResourceTable.Id_welcome_image);
        welcomeText = (Text) findComponentById(ResourceTable.Id_welcome_text);
        String firstName = intent.getStringParam("first name");
        String lastName = intent.getStringParam("last name");
        welcomeText.setText("Welcome " + firstName +" "+lastName);
        welcomeImage.setClickedListener(component -> animation.setAppLogoAnimation(welcomeImage));
        welcomeImage.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                animation.setAppLogoAnimation(welcomeImage);
            }
            @Override
            public void onComponentUnboundFromWindow(Component component) {
            }
        });
    }

    @Override
    public void onActive(){
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}


