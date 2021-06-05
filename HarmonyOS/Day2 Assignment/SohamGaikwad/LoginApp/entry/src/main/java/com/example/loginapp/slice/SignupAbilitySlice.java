package com.example.loginapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.loginapp.ResourceTable;
import ohos.agp.components.*;

import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {

    boolean check;

    TextField firstName, lastName, email, mobile, password;
    Text firstNameError, lastNameError, emailError, mobileError, passwordError, genderError;
    RadioButton maleRadio, femaleRadio;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        check = true;

        firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        lastName = (TextField) findComponentById(ResourceTable.Id_last_name);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        maleRadio = (RadioButton) findComponentById(ResourceTable.Id_radio_male);
        femaleRadio = (RadioButton) findComponentById(ResourceTable.Id_radio_female);

        firstNameError = (Text) findComponentById(ResourceTable.Id_first_name_error);
        lastNameError = (Text) findComponentById(ResourceTable.Id_last_name_error);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        genderError = (Text) findComponentById(ResourceTable.Id_gender_error);


        Button btn = (Button) findComponentById(ResourceTable.Id_signup_btn);




        btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                checkAll();
            }

        });
    }

    public static boolean isEmailValid(String email)
    {

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone)
    {

        String regex = "\\+?\\d[\\d -]{8,12}\\d";

        Pattern pat = Pattern.compile(regex);
        if (phone == null)
            return false;
        return pat.matcher(phone).matches();
    }

    private void checkAll() {
        check = true;
        firstNameError.setText(null);
        lastNameError.setText(null);
        emailError.setText(null);
        mobileError.setText(null);
        passwordError.setText(null);
        genderError.setText(null);

        if (firstName.length() == 0) {
            check = false;
            firstNameError.setText("Cannot be empty!");
        }

        if (lastName.length() == 0) {
            check = false;
            lastNameError.setText("Cannot be empty!");
        }

        if (password.length() == 0) {
            check = false;
            passwordError.setText("Cannot be empty!");
        }

        if (!isEmailValid(email.getText())) {
            check = false;
            emailError.setText("Invalid email");
        }

        if (!isPhoneValid(mobile.getText())) {
            check = false;
            mobileError.setText("Invalid phone");
        }

        if ((!maleRadio.isChecked()) && (!femaleRadio.isChecked())) {
            check = false;
            genderError.setText("Select gender required");
        }
    }


}
