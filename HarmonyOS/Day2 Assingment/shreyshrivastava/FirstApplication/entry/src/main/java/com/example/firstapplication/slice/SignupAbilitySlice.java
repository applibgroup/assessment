package com.example.firstapplication.slice;

import com.example.firstapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {
    TextField firstName;

    TextField lastName;
    Text errorLastName;

    TextField email;
    Text errorEmail;

    TextField password;
    Text errorPassword;

    TextField mobile;
    Text errorMobile;

    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    Text errorGender;

    Button buttonSignUp;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        firstName = (TextField)findComponentById(ResourceTable.Id_text_first_name);

        lastName = (TextField)findComponentById(ResourceTable.Id_text_last_name);
        errorLastName = (Text)findComponentById(ResourceTable.Id_text_error_last_name);

        email = (TextField)findComponentById(ResourceTable.Id_text_email);
        errorEmail = (Text)findComponentById(ResourceTable.Id_text_error_email);

        password = (TextField)findComponentById(ResourceTable.Id_text_password);
        errorPassword = (Text)findComponentById(ResourceTable.Id_text_error_password);

        mobile = (TextField)findComponentById(ResourceTable.Id_text_mobile);
        errorMobile = (Text)findComponentById(ResourceTable.Id_text_error_mobile);

        radioButtonMale = (RadioButton)findComponentById(ResourceTable.Id_radio_button_male);
        radioButtonFemale = (RadioButton)findComponentById(ResourceTable.Id_radio_button_female);
        errorGender = (Text)findComponentById(ResourceTable.Id_text_error_gender);

        buttonSignUp = (Button)findComponentById(ResourceTable.Id_button_signup);

        buttonSignUp.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String fName = firstName.getText().toString();

                String lName = lastName.getText().toString();
                Boolean validLastName = lName.chars().allMatch(Character::isLetter);
                if (!validLastName) {
                    errorLastName.setVisibility(0);
                } else {
                    errorLastName.setVisibility(1);
                }

                String eMail = email.getText().toString();
                Pattern pattern = Pattern.compile("^.+@.+\\..+$");
                Matcher matcher = pattern.matcher(eMail);
                Boolean validEmail = matcher.matches();
                if (!validEmail) {
                    errorEmail.setVisibility(0);
                } else {
                    errorEmail.setVisibility(1);
                }

                String passWord = password.getText().toString();
                Boolean validPassword = (Boolean) (passWord.length() >= 5);
                if (!validPassword) {
                    errorPassword.setVisibility(0);
                } else {
                    errorPassword.setVisibility(1);
                }

                String mobileNumber = mobile.getText().toString();
                Boolean validMobile = mobileNumber.chars().allMatch(Character::isDigit);
                if (!validMobile) {
                    errorMobile.setVisibility(0);
                } else {
                    errorMobile.setVisibility(1);
                }

                Boolean validGender = radioButtonMale.isChecked() ^ radioButtonFemale.isChecked();
                if (!validGender) {
                    errorGender.setVisibility(0);
                } else {
                    errorGender.setVisibility(1);
                }

                ToastDialog toastDialog = new ToastDialog(getContext());
                if (validLastName && validEmail && validPassword && validMobile && validGender) {
                    toastDialog.setText("Thank you, sign up completed.");
                } else {
                    toastDialog.setText("Please enter valid information.");
                }
                toastDialog.setAlignment(0);
                toastDialog.setOffset(0, 0);
                toastDialog.setDuration(10000);
                toastDialog.show();
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
