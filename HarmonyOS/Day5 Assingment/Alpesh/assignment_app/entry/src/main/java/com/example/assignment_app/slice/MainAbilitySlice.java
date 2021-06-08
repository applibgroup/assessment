package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;


public class MainAbilitySlice extends AbilitySlice implements Component.ClickedListener {



    private AnimatorValue button1Animation;
    private AnimatorProperty button2Animation;
    private AnimatorProperty button3Animation;
    private Button customDialogButton;
    private Image imageComponent;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        button1Animation = new AnimatorValue();
        button1Animation.setDuration(1000);
        button1Animation.setLoopedCount(2);
        button1Animation.setCurveType(Animator.CurveType.CUBIC_BEZIER_FRICTION);
        Button login = (Button) findComponentById(ResourceTable.Id_login_btn);
        Button slide = (Button) findComponentById(ResourceTable.Id_slide_btn);
        slide.setClickedListener(this::onClick);
        Button signup = (Button) findComponentById(ResourceTable.Id_signup_btn);
        imageComponent = (Image) findComponentById(ResourceTable.Id_img);
        Text main = (Text) findComponentById(ResourceTable.Id_main);
        button1Animation.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float value) {
                //animating button position
                slide.setContentPosition((int) (370 * value), slide.getContentPositionY());
            }
        });
        button2Animation = imageComponent.createAnimatorProperty();
        button2Animation.setDuration(2000).rotate(360).setLoopedCount(-1);
        button3Animation = main.createAnimatorProperty();
        button3Animation.setDuration(4000).moveFromX(-1000).moveToX(1000).setLoopedCount(-1);
        imageComponent.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                button2Animation.start();
                button3Animation.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                button2Animation.stop();
                button3Animation.stop();
            }});

        login.setClickedListener(List -> present(new Login(),new Intent()));
        signup.setClickedListener(List -> present(new Signup(),new Intent()));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    

    @Override
    public void onClick(Component component) {
        switch (component.getId()){
            case ResourceTable.Id_slide_btn:
                button1Animation.start();
                break;
        }
    }


    /*private void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("Common Dialog");
        commonDialog.setContentText("This is a common dialog");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
            showToast("Clicked Yes button");
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
            showToast("Clicked No button");
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON3, "Maybe", (iDialog, i) -> {
            showToast("Clicked Maybe button");
            iDialog.destroy();
        });
        commonDialog.show();
    }*/


    private void showToast(String msg) {
        new ToastDialog(this).setText(msg).setDuration(500).show();
    }
}
