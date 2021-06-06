package com.example.assignment.slice;

import com.example.assignment.DbUtils;
import com.example.assignment.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;


import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUpSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);

        Button b = (Button) findComponentById(ResourceTable.Id_button);
        b.setClickedListener((component -> {
            boolean ok = true;
            //VERIFICATION OF FIRST NAME
            String NAME = "[a-zA-Z]+";

            TextField text1 = (TextField) findComponentById(ResourceTable.Id_first_name_textField);
            String firstName = text1.getText();
            if(!Pattern.matches(NAME,firstName)) {
                Text error_text1 = (Text) findComponentById(ResourceTable.Id_error_tip_text);
                error_text1.setTextColor(Color.RED);
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                text1.setBackground(errorElement);
                text1.clearFocus();
                ok = false;
            }
            //VERIFICATION OF LAST NAME
            TextField text2 = (TextField) findComponentById(ResourceTable.Id_last_name_textField);
            String lastName = text2.getText();
            if(!Pattern.matches(NAME,lastName)) {
                Text error_text2 = (Text) findComponentById(ResourceTable.Id_error_tip_text2);
                error_text2.setTextColor(Color.RED);
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                text2.setBackground(errorElement);
                text2.clearFocus();
                ok = false;
            }

            //VERIFICATION OF EMAIL ADDRESS
            String EMAIL_STRING = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            TextField text3 = (TextField) findComponentById(ResourceTable.Id_email_textField);
            String email = text3.getText();
            if(!Pattern.matches(EMAIL_STRING,email)) {
                Text error_text3 = (Text) findComponentById(ResourceTable.Id_error_tip_text3);
                error_text3.setTextColor(Color.RED);
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                text3.setBackground(errorElement);
                text3.clearFocus();
                ok = false;
            }

            //VERIFICATION OF PASSWORD
            TextField text4 = (TextField) findComponentById(ResourceTable.Id_password_textField);
            String password = text4.getText();
            if(password.length() < 5) {
                Text error_text4 = (Text) findComponentById(ResourceTable.Id_error_tip_text4);
                error_text4.setTextColor(Color.RED);
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                text4.setBackground(errorElement);
                text4.clearFocus();
                ok = false;
            }

            //VERIFICATION OF PHONE NO
            TextField text5 = (TextField) findComponentById(ResourceTable.Id_phone_no_textField);
            String phone = text5.getText();
            String PHONE = "\\d{10}";
            if(!Pattern.matches(PHONE, phone)) {
                Text error_text5 = (Text) findComponentById(ResourceTable.Id_error_tip_text5);
                error_text5.setTextColor(Color.RED);
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                text5.setBackground(errorElement);
                text5.clearFocus();
                ok = false;
            }

            RadioButton male = (RadioButton) findComponentById(ResourceTable.Id_rb1);
            RadioButton female = (RadioButton) findComponentById(ResourceTable.Id_rb2);

            String gender;
            if(male.isChecked()) {
                gender = "MALE";
            }

            else {
                gender = "FEMALE";
            }

            if(ok) {
                ArrayList<String> dataArray = new ArrayList<>();
                dataArray.add(email);
                dataArray.add(password);
                dataArray.add(firstName + lastName);
                dataArray.add(phone);
                dataArray.add(gender);
                Text text6 = (Text) findComponentById(ResourceTable.Id_enquiry);

                DbUtils.insert(SignUpSlice.this, dataArray);
                text6.setTextColor(Color.WHITE);

                text1.setText("");
                text2.setText("");
                text3.setText("");
                text4.setText("");
                text5.setText("");
                male.setChecked(false);
                female.setChecked(false);
            }
        }));
    }
}
