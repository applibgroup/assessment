package com.assigment.myapp.slice;

import com.assigment.myapp.ResourceTable;
import com.assigment.myapp.Utils.PatternUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;

public class SignUpAbilitySlice extends AbilitySlice {
    Image appLogo;
    TextField firstName;
    TextField lastName;
    TextField emailField;
    TextField passwordField;
    TextField mobileField;
    RadioButton maleButton;
    RadioButton femaleButton;
    Text firstNameError;
    Text lastNameError;
    Text emailError;
    Text passwordError;
    Text mobileError;
    Button submit;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_signup_page);
        initViews();
        initListeners();
    }

    private void initViews() {
        firstName = (TextField) findComponentById(ResourceTable.Id_firstname);
        lastName = (TextField) findComponentById(ResourceTable.Id_lastname);
        emailField = (TextField) findComponentById(ResourceTable.Id_email);
        passwordField = (TextField) findComponentById(ResourceTable.Id_password);
        mobileField = (TextField) findComponentById(ResourceTable.Id_mobile);

        firstNameError = (Text) findComponentById(ResourceTable.Id_firstname_error);
        lastNameError = (Text) findComponentById(ResourceTable.Id_lastname_error);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);

        maleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        femaleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_female);

        submit = (Button) findComponentById(ResourceTable.Id_signup_btn);
        appLogo = (Image) findComponentById(ResourceTable.Id_signup_page_header);
        appLogo.setPixelMap(ResourceTable.Media_my_app);
        appLogo.setScaleMode(Image.ScaleMode.STRETCH);
    }

    private void initListeners() {
        firstName.addTextObserver((input,start,before, count)-> validateName(firstNameError,input));
        lastName.addTextObserver((input, start, before, count)-> validateName(lastNameError,input));
        emailField.addTextObserver((input, start, before, count) -> validateEmail(input));
        passwordField.addTextObserver((input, start, before, count) -> validatePassword(input));
        mobileField.addTextObserver((input, start, before, count) -> validateMobile(input));
        submit.setClickedListener(component -> handleSubmitClick(component));
    }

    /**
     * validates the firstname and last name
     *
     * @param errorText - error text object for the respective textfield
     * @param input - input string in the textfield
     */
    public void validateName(Text errorText, String input) {
        if (input == null) {
            return;
        }
        if (PatternUtils.isConsistOfNumber(input) || PatternUtils.isContainSpecialCharacter(input)) {
            errorText.setVisibility(Component.VISIBLE);
        } else {
            errorText.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates if its a valid email address or not
     *
     * @param email - email string entered by user
     */
    public void validateEmail(String email){
        if (email==null) {
            return;
        }
        if (!(email.contains("@") && email.contains(".com"))) {
            emailError.setVisibility(Component.VISIBLE);
        } else {
            emailError.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates if its a valid password or not. If the string>5 and contains a special character, then its valid
     *
     * @param password - password string entered by user
     */
    public void validatePassword(String password) {
        if (password == null) {
            return;
        }
        if (password.length()<5 || !PatternUtils.isContainSpecialCharacter(password)) {
            passwordError.setVisibility(Component.VISIBLE);
        } else {
            passwordError.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates mobile number. If the number is less that 10 digits and contains non numeric chars,
     * then its invalid
     *
     * @param mobile
     */
    public void validateMobile(String mobile) {
        if (mobile == null) {
            return;
        }
        if (mobile.length() < 10 || !PatternUtils.isContainOnlyNumber(mobile)) {
            mobileError.setVisibility(Component.VISIBLE);
        } else {
            mobileError.setVisibility(Component.HIDE);
        }
    }

    /**
     * checks if the form can be submitted or not
     *
     * @param component - button
     */
    public void handleSubmitClick(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (firstName.getText() == null
                || lastName.getText() == null
                || emailField.getText() == null
                || passwordField.getText() == null
                || mobileField.getText() == null
                || firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || emailField.getText().isEmpty()
                || mobileField.getText().isEmpty()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_error)).show();
        } else if (firstNameError.getVisibility() == Component.VISIBLE
                || lastNameError.getVisibility() == Component.VISIBLE
                || emailError.getVisibility() == Component.VISIBLE
                || passwordError.getVisibility() == Component.VISIBLE
                || mobileError.getVisibility() == Component.VISIBLE) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_error)).show();
        } else if (!maleButton.isChecked() && !femaleButton.isChecked()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_error_select_gender)).show();
        } else {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_success)).show();
        }
    }
}
