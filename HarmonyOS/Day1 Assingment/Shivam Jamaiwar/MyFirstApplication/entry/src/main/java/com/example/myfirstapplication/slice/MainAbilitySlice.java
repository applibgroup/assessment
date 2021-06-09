package com.example.myfirstapplication.slice;

import com.example.myfirstapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice {

    private Button mBtnLogin, mBtnSignup;
    private Image mImgIcon;
    private AnimatorValue iconAnimation;
    private AnimatorProperty loginBtnAnimation, signUpBtnAnimation;
    private AnimatorGroup animatorGroup;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        mBtnLogin = (Button) findComponentById(ResourceTable.Id_btn_login);
        mBtnSignup = (Button) findComponentById(ResourceTable.Id_btn_signup);
        mImgIcon = (Image) findComponentById(ResourceTable.Id_icon);

        mBtnLogin.setClickedListener(listener -> present(new LoginAbilitySlice(), new Intent()));
        mBtnSignup.setClickedListener(listener -> present(new SignupAbilitySlice(), new Intent()));

        animateComponents();

        /*RdbUtils rdbUtils = new RdbUtils(this);
        rdbUtils.insertData();
        rdbUtils.getData();*/
    }

    private void animateComponents() {

        //AnimatorValue Example
        iconAnimation = new AnimatorValue();
        iconAnimation.setDuration(2000);
        iconAnimation.setLoopedCount(0);
        iconAnimation.setCurveType(Animator.CurveType.BOUNCE);

        iconAnimation.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float value) {
                //animating button position
                mImgIcon.setContentPosition(mImgIcon.getContentPositionX(), (int) (500 * value));

                //animating width and height
                //targetButton1.setWidth((int) (500*value));
                //targetButton1.setHeight((int) (500*value));

                //animating alpha property
                //targetButton1.setAlpha(value);
            }
        });
        iconAnimation.start();

        //AnimatorGroup Example
        animatorGroup = new AnimatorGroup();
        animatorGroup.runSerially(
                mBtnLogin.createAnimatorProperty().
                        moveFromX(0).moveToX(mBtnLogin.getContentPositionX()),
                mBtnSignup.createAnimatorProperty().
                        moveFromX(1000).moveToX(mBtnSignup.getContentPositionX())
        );
        animatorGroup.setDuration(2000);
        animatorGroup.setLoopedCount(1);
        animatorGroup.start();

        //AnimatorProperty Example
        /*loginBtnAnimation = mBtnLogin.createAnimatorProperty();  //0r button2Animation.setTarget(targetButton2)
        loginBtnAnimation.setDuration(2000).moveFromX(0).moveToX(1000).alpha(0).setLoopedCount(1);
        loginBtnAnimation.start();

        signUpBtnAnimation = mBtnSignup.createAnimatorProperty();  //0r button2Animation.setTarget(targetButton2)
        signUpBtnAnimation.setDuration(2000).moveFromX(1000).moveToX(500).alpha(0).setLoopedCount(1);
        signUpBtnAnimation.start();*/
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
