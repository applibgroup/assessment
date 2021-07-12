package com.example.loginscreenapplication.slice;
import com.example.loginscreenapplication.DbHelper;
import com.example.loginscreenapplication.Utils.InputValidationMethods;
import com.example.loginscreenapplication.Model.RegisterResponse;
import com.example.loginscreenapplication.Model.UserData;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.rdb.RdbStore;

public class RegisterAbilitySlice  extends AbilitySlice {
    public static final String ALL_FIELDS_ARE_REQUIRED = "All fields are required.";
    public static final String ENSURE_VALID_INPUTS_TO_ALL_FIELDS = "Ensure valid inputs to all fields.";
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String USER_DATA = "UserData";
    public static final String SUBMISSION_FAILED = "Submission Failed. Email Id already exists in database.";
    Text errorFirstName;
    Text errorLastName;
    Text errorEmail;
    Text errorPassword;
    Text errorMobile;
    Text registerFailedMessage;

    TextField inputFirstName;
    TextField inputLastName;
    TextField inputEmail;
    TextField inputPassword;
    TextField inputMobile;
    RadioButton inputGenderMale;
    RadioButton inputGenderFemale;

    Button submitButton;

    private RdbStore db;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_register_layout);

        initializeFieldsAndListeners();

    }

    @Override
    public void onActive() {
        super.onActive();
        DbHelper dbHelper = new DbHelper(this);
        db = dbHelper.initRdb(this);
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void initializeFieldsAndListeners() {
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
        registerFailedMessage = (Text)findComponentById(ResourceTable.Id_registerFailedMessage);
        registerFailedMessage.setVisibility(Component.INVISIBLE);

        inputFirstName = (TextField)findComponentById(ResourceTable.Id_registerFirstName);
        inputLastName = (TextField)findComponentById(ResourceTable.Id_registerLastName);
        inputEmail = (TextField)findComponentById(ResourceTable.Id_registerEmail);
        inputPassword = (TextField)findComponentById(ResourceTable.Id_registerPassword);
        inputMobile = (TextField)findComponentById(ResourceTable.Id_registerMobile);
        inputGenderMale = (RadioButton)findComponentById(ResourceTable.Id_registerGenderMale);
        inputGenderFemale = (RadioButton)findComponentById(ResourceTable.Id_registerGenderFemale);

        submitButton = (Button)findComponentById(ResourceTable.Id_registerSubmitButton);

        inputFirstName.addTextObserver((s, i, i1, i2) -> InputValidationMethods.checkForNameError(s, errorFirstName));
        inputLastName.addTextObserver((s, i, i1, i2) -> InputValidationMethods.checkForNameError(s, errorLastName));
        inputEmail.addTextObserver((s, i, i1, i2) -> InputValidationMethods.checkForEmailError(s, errorEmail));
        inputPassword.addTextObserver((s, i, i1, i2) -> InputValidationMethods.checkForPasswordError(s, errorPassword));
        inputMobile.addTextObserver((s, i, i1, i2) -> InputValidationMethods.checkForMobileError(s, errorMobile));
        submitButton.setClickedListener(this::onClickSubmit);
    }

    private void onClickSubmit(Component component)
    {
        // Check if the text fields all have entries:
        if (isNullOrEmpty(inputFirstName.getText()) ||
                isNullOrEmpty(inputLastName.getText()) ||
                isNullOrEmpty(inputEmail.getText()) ||
                isNullOrEmpty(inputPassword.getText()) ||
                isNullOrEmpty(inputMobile.getText()) ||
                (!inputGenderMale.isChecked() && !inputGenderFemale.isChecked()))
        {
            registerFailedMessage.setVisibility(Component.VISIBLE);
            registerFailedMessage.setText(ALL_FIELDS_ARE_REQUIRED);
        }
        else
        {
            // Check if the text fields are all validated
            if (errorFirstName.getVisibility() == Component.VISIBLE ||
                    errorLastName.getVisibility() == Component.VISIBLE ||
                    errorEmail.getVisibility() == Component.VISIBLE ||
                    errorPassword.getVisibility() == Component.VISIBLE ||
                    errorMobile.getVisibility() == Component.VISIBLE)
            {
                registerFailedMessage.setVisibility(Component.VISIBLE);
                registerFailedMessage.setText(ENSURE_VALID_INPUTS_TO_ALL_FIELDS);
            }
            else
            {
                // All inputs validated. Data submission can proceed
                RegisterResponse registerResponse = submitData();
                if (registerResponse.getRegisterResponseType() == RegisterResponse.RegisterResponseType.SUCCESS)
                {
                    // Submission was a success. Redirect to home screen
                    Intent intent = new Intent();
                    intent.setParam(REGISTRATION_SUCCESS, true);
                    intent.setParam(USER_DATA, registerResponse.getUserData());
                    present(new MainAbilitySlice(), intent);
                }
                else
                {
                    registerFailedMessage.setVisibility(Component.VISIBLE);
                    registerFailedMessage.setText(SUBMISSION_FAILED);
                }
            }
        }
    }

    private RegisterResponse submitData()
    {
        UserData userData = getUserDataFromTextFields();
        boolean submissionResult = DbHelper.insertUserData(db, userData);
        RegisterResponse.RegisterResponseType registerResponseType;
        if (submissionResult)
            registerResponseType = RegisterResponse.RegisterResponseType.SUCCESS;
        else
            registerResponseType = RegisterResponse.RegisterResponseType.FAIL;
        return new RegisterResponse(userData, registerResponseType);
    }

    private UserData getUserDataFromTextFields()
    {
        UserData userData = new UserData();
        userData.setFirstName(inputFirstName.getText());
        userData.setLastName(inputLastName.getText());
        userData.setEmail(inputEmail.getText());
        userData.setPassword(inputPassword.getText());
        userData.setMobile(inputMobile.getText());
        if (inputGenderMale.isChecked())
            userData.setGender(UserData.Gender.Male);
        else
            userData.setGender(UserData.Gender.Female);
        return userData;
    }

    private boolean isNullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

}