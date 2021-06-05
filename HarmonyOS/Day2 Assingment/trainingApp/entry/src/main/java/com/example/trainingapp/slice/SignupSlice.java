package com.example.trainingapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.trainingapp.ResourceTable;
import ohos.agp.components.*;
import java.util.regex.Pattern;

public class SignupSlice extends AbilitySlice {
    TextField firstName, lastName, email, password, mobile;
    Text firstError, lastError, emailError, passError, mobileError, radioError;
    RadioButton maleRadio,femaleRadio;
    Button signupBtn;
    @Override
    protected void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);

        firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        lastName = (TextField) findComponentById((ResourceTable.Id_last_name));
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        mobile = (TextField) findComponentById(ResourceTable.Id_mobile);

        maleRadio = (RadioButton) findComponentById(ResourceTable.Id_male);
        femaleRadio = (RadioButton) findComponentById(ResourceTable.Id_female);

        firstError = (Text) findComponentById(ResourceTable.Id_firstError);
        lastError = (Text) findComponentById(ResourceTable.Id_lastError);
        emailError = (Text) findComponentById(ResourceTable.Id_emailError);
        passError = (Text) findComponentById(ResourceTable.Id_passwordError);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobileError);
        radioError = (Text) findComponentById(ResourceTable.Id_radioError);

        signupBtn =(Button) findComponentById(ResourceTable.Id_signup);

        signupBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                checkValidity();
            }
        });

    }
    public boolean isNameValid(TextField name){
        String str = name.getText();
        String regex = "[a-zA-Z]+";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isEmailValid(TextField email){
        String str = email.getText();
        String regex = ".+@.+\\..+";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isPhoneValid(TextField mobile){
        String str = mobile.getText();
        String regex = "[1-9][0-9]{9}";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isPassValid(TextField password){
        String str = password.getText();
        String regex = ".{5,}";
        Pattern p = Pattern.compile(regex);
        return p.matcher(str).matches();
    }

    public void checkValidity(){
        if(firstName.getText() == null || firstName.getText().isEmpty() || !isNameValid(firstName)){
            firstError.setVisibility(Component.VISIBLE);
        }
        else{
            firstError.setVisibility(Component.INVISIBLE);
        }
        if(lastName.getText() == null || lastName.getText().isEmpty() || !isNameValid(lastName)){
            lastError.setVisibility(Component.VISIBLE);
        }
        else{
            lastError.setVisibility(Component.INVISIBLE);
        }
        if(email.getText() == null || email.getText().isEmpty() || !isEmailValid(email)){
            emailError.setVisibility(Component.VISIBLE);
        }
        else{
            emailError.setVisibility(Component.INVISIBLE);
        }
        if(mobile.getText() == null || mobile.getText().isEmpty() || !isPhoneValid(mobile)){
            mobileError.setVisibility(Component.VISIBLE);
        }
        else{
            mobileError.setVisibility(Component.INVISIBLE);
        }
        if(password.getText() == null || password.getText().isEmpty() || !isPassValid(password)){
            passError.setVisibility(Component.VISIBLE);
        }
        else{
            passError.setVisibility(Component.INVISIBLE);
        }
        if(!maleRadio.isChecked() && !femaleRadio.isChecked()){
            radioError.setVisibility(Component.VISIBLE);
        }
        else{
            radioError.setVisibility(Component.INVISIBLE);
        }
    }
}
