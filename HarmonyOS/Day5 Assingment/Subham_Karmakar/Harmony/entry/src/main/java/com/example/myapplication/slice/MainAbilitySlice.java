package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorScatter;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;

public class MainAbilitySlice extends AbilitySlice
{
    private Image image;
    private Button signup;
    private Button login;
    private AnimatorValue animatorValue;

    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        animatorValue = new AnimatorValue();

        AnimatorScatter scatter = AnimatorScatter.getInstance(getContext());
        Animator animator = scatter.parse(ResourceTable.Animation_animator_value);
        if(animator instanceof AnimatorValue)
        {
            animatorValue = (AnimatorValue) animator;
            animatorValue.setLoopedCount(3);
            animatorValue.setCurveType(Animator.CurveType.BOUNCE);
            animatorValue.setDuration(2500);
            animatorValue.setDelay(0);
        }

        animatorValue.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float value)
            {
                image.setContentPosition(image.getContentPositionX(), (int) (400*value));
            }
        });

        animatorValue.start();

        signup = (Button) findComponentById(ResourceTable.Id_signup);
        login = (Button) findComponentById(ResourceTable.Id_login);
        image = (Image) findComponentById(ResourceTable.Id_img);

        signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new SignupAbilitySlice(), new Intent());
            }
        });

        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new LoginAbilitySlice(), new Intent());
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);

    }
}
