package com.example.loginscreenapplication.slice;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAbilitySlice  extends AbilitySlice {
    Text errorFirstName;
    Text errorLastName;
    Text errorEmail;
    Text errorPassword;
    Text errorMobile;

    TextField inputFirstName;
    TextField inputLastName;
    TextField inputEmail;
    TextField inputPassword;
    TextField inputMobile;
    RadioButton inputGenderMale;
    RadioButton inputGenderFemale;

    Button submitButton;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_register_layout);

        errorFirstName = (Text)findComponentById(ResourceTable.Id_errorFirstName);
        errorFirstName.setVisibility(Component.INVISIBLE);
        errorLastName = (Text)findComponentById(ResourceTable.Id_errorLastName);
        errorLastName.setVisibility(Component.INVISIBLE);
        errorEmail = (Text)findComponentById(ResourceTable.Id_errorEmail);
        errorEmail.setVisibility(Component.INVISIBLE);
        errorPassword = (Text)findComponentById(ResourceTable.Id_errorPassword);
        errorPassword.setVisibility(Component.INVISIBLE);
        errorMobile = (Text)findComponentById(ResourceTable.Id_errorMobile);
        errorMobile.setVisibility(Component.INVISIBLE);

        inputFirstName = (TextField)findComponentById(ResourceTable.Id_registerFirstName);
        inputLastName = (TextField)findComponentById(ResourceTable.Id_registerLastName);
        inputEmail = (TextField)findComponentById(ResourceTable.Id_registerEmail);
        inputPassword = (TextField)findComponentById(ResourceTable.Id_registerPassword);
        inputMobile = (TextField)findComponentById(ResourceTable.Id_registerMobile);
        inputGenderMale = (RadioButton)findComponentById(ResourceTable.Id_registerGenderMale);
        inputGenderFemale = (RadioButton)findComponentById(ResourceTable.Id_registerGenderFemale);

        submitButton = (Button)findComponentById(ResourceTable.Id_registerSubmitButton);

        inputFirstName.addTextObserver((s, i, i1, i2) -> checkForNameError(s, errorFirstName));
        inputLastName.addTextObserver((s, i, i1, i2) -> checkForNameError(s, errorLastName));
        inputEmail.addTextObserver((s, i, i1, i2) -> checkForEmailError(s, errorEmail));
        inputPassword.addTextObserver((s, i, i1, i2) -> checkForPasswordError(s, errorPassword));
        inputMobile.addTextObserver((s, i, i1, i2) -> checkForMobileError(s, errorMobile));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void checkForNameError(String inputName, Text targetErrorText)
    {
        Pattern namePattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher = namePattern.matcher(inputName);
        boolean isValidName = !matcher.find();
        if (!isValidName)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
    private void checkForEmailError(String inputEmail, Text targetErrorText)
    {
        Pattern mailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+");
        Matcher matcher = mailPattern.matcher(inputEmail);
        boolean isValidEmail = matcher.find();
        if (!isValidEmail)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
    private void checkForPasswordError(String inputPassword, Text targetErrorText)
    {
        boolean isValidPassword = inputPassword.length() >= 8;
        if (!isValidPassword)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
    private void checkForMobileError(String inputMobile, Text targetErrorText)
    {
        Pattern nonNumericPattern = Pattern.compile("[^0-9]");
        Matcher matcher = nonNumericPattern.matcher(inputMobile);
        boolean isValidMobile = (!matcher.find())&&(inputMobile.length() >= 1);
        if (!isValidMobile)
            targetErrorText.setVisibility(Component.VISIBLE);
        else
            targetErrorText.setVisibility(Component.INVISIBLE);
    }
}