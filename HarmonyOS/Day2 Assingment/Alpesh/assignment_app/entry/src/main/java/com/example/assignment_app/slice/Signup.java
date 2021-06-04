package com.example.assignment_app.slice;

import com.example.assignment_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

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

        firstname.setFocusChangedListener((component, isFocused) -> {
            String name = firstname.getText();
            if (!isFocused) {
                if (!name.matches("^[a-zA-Z]*$")) {
                    //firstname.setText("");
                    Text er1 = (Text) findComponentById(ResourceTable.Id_er1);
                    er1.setVisibility(Component.VISIBLE);
                }
                else
                {
                    Text er1 = (Text) findComponentById(ResourceTable.Id_er1);
                    er1.setVisibility(Component.INVISIBLE);
                }

            }
        });


        lastname.setFocusChangedListener((component, isFocused) -> {
            String name = lastname.getText();
            if (!isFocused) {
                if (!name.matches("^[a-zA-Z]*$")) {
                    //lastname.setText("");
                    Text er2 = (Text) findComponentById(ResourceTable.Id_er2);
                    er2.setVisibility(Component.VISIBLE);
                }
                else
                {
                    Text er2 = (Text) findComponentById(ResourceTable.Id_er2);
                    er2.setVisibility(Component.INVISIBLE);
                }

            }
        });


        email.setFocusChangedListener((component, isFocused) -> {
            String name = email.getText();
            if (!isFocused) {
                if (!name.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
                    //lastname.setText("");
                    Text er3 = (Text) findComponentById(ResourceTable.Id_er3);
                    er3.setVisibility(Component.VISIBLE);
                }
                else
                {
                    Text er3 = (Text) findComponentById(ResourceTable.Id_er3);
                    er3.setVisibility(Component.INVISIBLE);
                }

            }
        });

        password.setFocusChangedListener((component, isFocused) -> {
            String name = password.getText();
            if (!isFocused) {
                if (!name.matches("^.{5,}$")) {
                    //lastname.setText("");
                    Text er4 = (Text) findComponentById(ResourceTable.Id_er4);
                    er4.setVisibility(Component.VISIBLE);
                }
                else
                {
                    Text er4 = (Text) findComponentById(ResourceTable.Id_er4);
                    er4.setVisibility(Component.INVISIBLE);
                }

            }
        });

        mobile.setFocusChangedListener((component, isFocused) -> {
            String name = mobile.getText();
            if (!isFocused) {
                if (!name.matches("^[0-9]{10}$")) {
                    //lastname.setText("");
                    Text er5 = (Text) findComponentById(ResourceTable.Id_er5);
                    er5.setVisibility(Component.VISIBLE);
                }
                else
                {
                    Text er5 = (Text) findComponentById(ResourceTable.Id_er5);
                    er5.setVisibility(Component.INVISIBLE);
                }

            }
        });

    }
}
