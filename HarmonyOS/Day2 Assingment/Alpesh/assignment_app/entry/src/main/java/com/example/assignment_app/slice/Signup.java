package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;


public class Signup extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);
        TextField firstname = (TextField) findComponentById(ResourceTable.Id_firstname);
        TextField lastname = (TextField) findComponentById(ResourceTable.Id_lastname);
        TextField email = (TextField) findComponentById(ResourceTable.Id_email);
        TextField password = (TextField) findComponentById(ResourceTable.Id_password);
        TextField mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        RadioButton male = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        RadioButton female = (RadioButton) findComponentById(ResourceTable.Id_gender_female);
        Button signup = (Button) findComponentById(ResourceTable.Id_signup_btn);
        Text er1 = (Text) findComponentById(ResourceTable.Id_er1);
        Text er2 = (Text) findComponentById(ResourceTable.Id_er2);
        Text er3 = (Text) findComponentById(ResourceTable.Id_er3);
        Text er4 = (Text) findComponentById(ResourceTable.Id_er4);
        Text er5 = (Text) findComponentById(ResourceTable.Id_er5);
        Text er6 = (Text) findComponentById(ResourceTable.Id_er6);

        // All field cleared
        firstname.setText("");
        lastname.setText("");
        email.setText("");
        password.setText("");
        mobile.setText("");
        male.setChecked(false);
        female.setChecked(false);
        er1.setVisibility(Component.INVISIBLE);
        er2.setVisibility(Component.INVISIBLE);
        er3.setVisibility(Component.INVISIBLE);
        er4.setVisibility(Component.INVISIBLE);
        er5.setVisibility(Component.INVISIBLE);
        er6.setVisibility(Component.INVISIBLE);

        firstname.setFocusChangedListener((component, isFocused) -> {
            String name = firstname.getText();
            if (!isFocused) {
                if (!name.matches(getString(ResourceTable.String_name_regex))) {

                    er1.setVisibility(Component.VISIBLE);
                }
                else
                {
                    er1.setVisibility(Component.INVISIBLE);
                }

            }
        });

        male.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                female.setChecked(false);
            }
        });

        female.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                male.setChecked(false);
            }
        });

        lastname.setFocusChangedListener((component, isFocused) -> {
            String name = lastname.getText();
            if (!isFocused) {
                if (!name.matches(getString(ResourceTable.String_name_regex))) {


                    er2.setVisibility(Component.VISIBLE);
                }
                else
                {

                    er2.setVisibility(Component.INVISIBLE);
                }

            }
        });


        email.setFocusChangedListener((component, isFocused) -> {
            String name = email.getText();
            if (!isFocused) {
                if (!name.matches(getString(ResourceTable.String_email_regex))) {

                    er3.setVisibility(Component.VISIBLE);
                }
                else
                {

                    er3.setVisibility(Component.INVISIBLE);
                }

            }
        });

        password.setFocusChangedListener((component, isFocused) -> {
            String name = password.getText();
            if (!isFocused) {
                if (!name.matches(getString(ResourceTable.String_pass_regex))) {

                    er4.setVisibility(Component.VISIBLE);
                }
                else
                {

                    er4.setVisibility(Component.INVISIBLE);
                }

            }
        });

        mobile.setFocusChangedListener((component, isFocused) -> {
            String name = mobile.getText();
            if (!isFocused) {
                if (!name.matches(getString(ResourceTable.String_mobile_regex))) {


                    er5.setVisibility(Component.VISIBLE);
                }
                else
                {

                    er5.setVisibility(Component.INVISIBLE);
                }

            }
        });


        signup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if ((!male.isChecked()) && (!female.isChecked()) )
                {
                    er6.setVisibility(Component.VISIBLE);
                }
                else
                {
                    er6.setVisibility(Component.INVISIBLE);
                }

                // checking if focus was on some feild and signup btn pressed, verifying that data format
                //
                if (firstname.getText().matches(getString(ResourceTable.String_name_regex))) {
                    er1.setVisibility(Component.INVISIBLE);
                }
                else
                {
                    er1.setVisibility(Component.VISIBLE);
                }
                if (lastname.getText().matches(getString(ResourceTable.String_name_regex))) {

                    er2.setVisibility(Component.INVISIBLE);
                }else
                {
                    er2.setVisibility(Component.VISIBLE);
                }
                if (email.getText().matches(getString(ResourceTable.String_email_regex))) {

                    er3.setVisibility(Component.INVISIBLE);
                }else
                {
                    er3.setVisibility(Component.VISIBLE);
                }
                if (password.getText().matches(getString(ResourceTable.String_pass_regex))) {

                    er4.setVisibility(Component.INVISIBLE);
                }else
                {
                    er4.setVisibility(Component.VISIBLE);
                }
                if (mobile.getText().matches(getString(ResourceTable.String_mobile_regex))) {

                    er5.setVisibility(Component.INVISIBLE);
                }else
                {
                    er5.setVisibility(Component.VISIBLE);
                }


                // Checking If anything empty

                if(email.getText().equals("") || password.getText().equals("") || firstname.getText().equals("") || lastname.getText().equals("") || mobile.getText().equals(""))
                {
                    new ToastDialog(getContext()).setText("Enter All Data").show();
                    //present(new Signup(), new Intent());
                }
                else if(er1.getVisibility()==0 || er2.getVisibility()==0 || er3.getVisibility()==0 || er4.getVisibility()==0 || er5.getVisibility()==0 || er6.getVisibility()==0)
                {
                    new ToastDialog(getContext()).setText("Enter Correct data and gender").show();
                }

                else {
                    new ToastDialog(getContext()).setText("Verified").show();
                    present(new MainAbilitySlice(), new Intent());
                }
            }
        });
    }

}
