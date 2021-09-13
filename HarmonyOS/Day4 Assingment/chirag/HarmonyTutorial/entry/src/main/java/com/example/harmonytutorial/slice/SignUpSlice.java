package com.example.harmonytutorial.slice;

import com.example.harmonytutorial.InputValidation;
import com.example.harmonytutorial.ResourceTable;
import com.example.harmonytutorial.StoreDB;
import com.example.harmonytutorial.UserEntity;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class SignUpSlice extends AbilitySlice {
    private DatabaseHelper dbHelper;
    private OrmContext ormContext;
    private TextField firstName;
    private TextField lastName;
    private TextField emailField;
    private TextField passwordField;
    private TextField mobileField;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private RadioButton otherButton;
    private Text firstNameError;
    private Text lastNameError;
    private Text emailError;
    private Text passwordError;
    private Text mobileError;
    private Button signup;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
        initViews();
        initListeners();
        dbHelper = new DatabaseHelper(this);
        ormContext = dbHelper.getOrmContext("StoreDB", "StoreDB.db", StoreDB.class);

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
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
        passwordError = (Text)  findComponentById(ResourceTable.Id_password_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);

        maleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        femaleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        otherButton = (RadioButton) findComponentById(ResourceTable.Id_gender_other);

        signup = (Button) findComponentById(ResourceTable.Id_signup_button);
    }

    private void initListeners() {
        firstName.addTextObserver((input, start, before, count) -> validateName(firstNameError, input));
        lastName.addTextObserver((input, start, before, count) -> validateName(lastNameError, input));
        emailField.addTextObserver((input, start, before, count) -> validateEmail(input));
        passwordField.addTextObserver((input, start, before, count) -> validatePassword(input));
        mobileField.addTextObserver((input, start, before, count) -> validateMobile(input));
        signup.setClickedListener(component -> handleSubmitClick(component));
    }

    /**
     * validates the firstname and last name
     *
     * @param errorText - error text object for the respective textfield
     * @param input     - input string in the textfield
     */
    public void validateName(Text errorText, String input) {
        if (input == null) {
            return;
        }
        if (InputValidation.isConsistOfNumber(input) || InputValidation.isContainSpecialCharacter(input)) {
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
    public void validateEmail(String email) {
        if (email == null) {
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
        if (password.length() < 5 || !InputValidation.isContainSpecialCharacter(password)) {
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
        if (mobile.length() < 10 || !InputValidation.isContainOnlyNumber(mobile)) {
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
            toastDialog.setText("Please fill all the fields").show();
        } else if (firstNameError.getVisibility() == Component.VISIBLE
                || lastNameError.getVisibility() == Component.VISIBLE
                || emailError.getVisibility() == Component.VISIBLE
                || passwordError.getVisibility() == Component.VISIBLE
                || mobileError.getVisibility() == Component.VISIBLE) {
            toastDialog.setText("Please fill the fields correctly").show();
        } else if (!maleButton.isChecked() && !femaleButton.isChecked() && !otherButton.isChecked()) {
            toastDialog.setText("Please select the gender").show();
        } else {
            if (emailAlreadyTaken(emailField.getText())) {
                new ToastDialog(this).setDuration(1000).setText("Email already taken, Use another email").show();
                return;
            }
            UserEntity u = new UserEntity();
            u.setFirstName(firstName.getText());
            u.setLastName(lastName.getText());
            u.setEmail(emailField.getText());
            u.setPassword(passwordField.getText());
            u.setMobile(mobileField.getText());
            if (maleButton.isChecked()) {
                u.setMale(true);
            } else if (femaleButton.isChecked()) {
                u.setFemale(true);
            } else {
                u.setOther(true);
            }
            boolean success = ormContext.insert(u);
            ormContext.flush();
            if (success) {
                Intent intent = new Intent();
                intent.setParam("first name",u.getFirstName());
                intent.setParam("last name", u.getLastName());
                present(new SuccessSignUpSlice(), new Intent());
            } else {
                new ToastDialog(this).setDuration(1000).setText("Error Occurred").show();
            }
        }
    }
    public boolean emailAlreadyTaken(String email){
        OrmPredicates pred = ormContext.where(UserEntity.class);
        pred.equalTo("email", email);
        List<UserEntity> uList = ormContext.query(pred);
        if(uList.size() > 0)
            return true;
        else
            return false;
    }
}

