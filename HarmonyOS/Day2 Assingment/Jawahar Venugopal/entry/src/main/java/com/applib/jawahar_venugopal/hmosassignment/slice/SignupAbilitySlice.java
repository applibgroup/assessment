package com.applib.jawahar_venugopal.hmosassignment.slice;

import com.applib.jawahar_venugopal.hmosassignment.ResourceTable;
import com.applib.jawahar_venugopal.hmosassignment.customview.CustomButton;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.utils.CommonPattern;

public class SignupAbilitySlice extends AbilitySlice implements Component.FocusChangedListener {

    private static final String NAME_REGEX_PATTERN = "^[a-zA-Z]*$";
    private static final String PASSWORD_REGEX_PATTERN = "^.{5,}$";
    private static final String MOBILE_REGEX_PATTERN = "^[0-9]{10}";

    private TextField mFirstNameField;
    private TextField mLastNameField;
    private TextField mEmailField;
    private TextField mPasswordField;
    private TextField mMobileField;

    private Text mFirstNameErrorText;
    private Text mLastNameErrorText;
    private Text mEmailErrorText;
    private Text mPasswordErrorText;
    private Text mMobileErrorText;

    private RadioContainer mGenderContainer;
    private CustomButton mSignupButton;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
        initViews();
    }

    private void initViews() {
        mFirstNameField = (TextField) findComponentById(ResourceTable.Id_first_name);
        mLastNameField = (TextField) findComponentById(ResourceTable.Id_last_name);
        mEmailField = (TextField) findComponentById(ResourceTable.Id_email);
        mPasswordField = (TextField) findComponentById(ResourceTable.Id_password);
        mMobileField = (TextField) findComponentById(ResourceTable.Id_mobile);

        mFirstNameErrorText = (Text) findComponentById(ResourceTable.Id_first_name_error);
        mLastNameErrorText = (Text) findComponentById(ResourceTable.Id_last_name_error);
        mEmailErrorText = (Text) findComponentById(ResourceTable.Id_email_error);
        mPasswordErrorText = (Text) findComponentById(ResourceTable.Id_password_error);
        mMobileErrorText = (Text) findComponentById(ResourceTable.Id_mobile_error);

        mGenderContainer = (RadioContainer) findComponentById(ResourceTable.Id_gender_container);
        mSignupButton = (CustomButton) findComponentById(ResourceTable.Id_signup);
        mSignupButton.setClickedListener(view -> validateAndSignup());

        mFirstNameField.setFocusChangedListener(this);
        mLastNameField.setFocusChangedListener(this);
        mEmailField.setFocusChangedListener(this);
        mPasswordField.setFocusChangedListener(this);
        mMobileField.setFocusChangedListener(this);

    }

    private void validateAndSignup() {
        String firstName = mFirstNameField.getText();
        Boolean allowSignup = validateUserInput(firstName, NAME_REGEX_PATTERN, mFirstNameErrorText);

        String lastName = mLastNameField.getText();
        allowSignup &= validateUserInput(lastName, NAME_REGEX_PATTERN, mLastNameErrorText);

        String email = mEmailField.getText();
        allowSignup &= validateUserInput(email, CommonPattern.getEmailAddress().pattern(), mEmailErrorText);

        String password = mPasswordField.getText();
        allowSignup &= validateUserInput(password, PASSWORD_REGEX_PATTERN, mPasswordErrorText);

        String mobile = mMobileField.getText();
        allowSignup &= validateUserInput(mobile, MOBILE_REGEX_PATTERN, mMobileErrorText);
        // TO-DO: Sign-up user if allowSignup True
    }

    private boolean validateUserInput(String string, String regexEx, Text errorTextComponent) {
        if (string != null && !string.isEmpty() && string.matches(regexEx)) {
            errorTextComponent.setVisibility(Component.HIDE);
            return true;
        } else {
            errorTextComponent.setVisibility(Component.VISIBLE);
            return false;
        }
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
    public void onFocusChange(Component component, boolean hasFocus) {
        if (hasFocus) {
            return;
        }
        switch (component.getId()) {
            case ResourceTable.Id_first_name:
                validateUserInput(mFirstNameField.getText(), NAME_REGEX_PATTERN, mFirstNameErrorText);
                break;
            case ResourceTable.Id_last_name:
                validateUserInput(mLastNameField.getText(), NAME_REGEX_PATTERN, mLastNameErrorText);
                break;
            case ResourceTable.Id_email:
                validateUserInput(mEmailField.getText(), CommonPattern.getEmailAddress().pattern(), mEmailErrorText);
                break;
            case ResourceTable.Id_password:
                validateUserInput(mPasswordField.getText(), PASSWORD_REGEX_PATTERN, mPasswordErrorText);
                break;
            case ResourceTable.Id_mobile:
                validateUserInput(mMobileField.getText(), MOBILE_REGEX_PATTERN, mMobileErrorText);
                break;
        }
    }
}
