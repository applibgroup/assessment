package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.Model.UserData;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.logging.Logger;

import static com.example.loginscreenapplication.Utils.UserDataHelperMethods.getFullName;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class MainAbilitySlice extends AbilitySlice {
    public static final String REGISTRATION_SUCCESS_INTENT_PARAM = "RegistrationSuccess";
    public static final String USER_DATA = "UserData";
    public static final String REGISTRATION_SUCCESS_DIALOG_TITLE = "Success!";
    public static final String REGISTRATION_SUCCESS_DIALOG_CONTENT_TEXT =
            "%s, you have successfully registered!";
    public static final float IMAGE_ANIM_ROTATION_AMOUNT = 360f;
    public static final int IMAGE_ANIM_DURATION = 4000;
    public static final int IMAGE_ANIM_DELAY = 500;
    public static final String DIALOG_OK = "OK";

    Button loginButton;
    Button registerButton;
    Image imageLogo;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        loginButton = (Button)findComponentById(ResourceTable.Id_loginButton);
        registerButton = (Button)findComponentById(ResourceTable.Id_registerButton);
        imageLogo = (Image)findComponentById(ResourceTable.Id_imageLogo);
        AnimatorProperty imageAnimatorProperty = createRotationAnimation(imageLogo);

        imageLogo.setClickedListener(component -> {
            imageAnimatorProperty.reset();
            imageAnimatorProperty.start();
        });
        loginButton.setClickedListener(component -> present(new LoginAbilitySlice(), new Intent()));
        registerButton.setClickedListener(component -> present(new RegisterAbilitySlice(), new Intent()));

        // Check for intent parameter passed after successful registration
        if (intent.getBooleanParam(REGISTRATION_SUCCESS_INTENT_PARAM, false))
        {
            UserData userData = intent.getSerializableParam(USER_DATA);
            if (userData != null)
            {
                showRegistrationSuccessDialogBox(userData);
            }
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private AnimatorProperty createRotationAnimation(Image targetImage)
    {
        AnimatorProperty animatorProperty = targetImage.createAnimatorProperty()
                .setCurveType(Animator.CurveType.ANTICIPATE_OVERSHOOT)
                .rotate(IMAGE_ANIM_ROTATION_AMOUNT).setDuration(IMAGE_ANIM_DURATION).setDelay(IMAGE_ANIM_DELAY);
        return animatorProperty;
    }

    private void showRegistrationSuccessDialogBox(UserData userData)
    {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText(REGISTRATION_SUCCESS_DIALOG_TITLE);

        String contentText = String.format(REGISTRATION_SUCCESS_DIALOG_CONTENT_TEXT,
                getFullName(userData));

        commonDialog.setContentText(contentText);
        commonDialog.setAutoClosable(true);
        commonDialog.setButton(IDialog.BUTTON1, DIALOG_OK, (iDialog, i) -> {
            commonDialog.destroy();
        });
        commonDialog.show();
    }
}
