package com.example.loginscreenapplication.slice;

import com.example.loginscreenapplication.DbHelper;
import com.example.loginscreenapplication.Utils.InputValidationMethods;
import com.example.loginscreenapplication.Model.LoginResponse;
import com.example.loginscreenapplication.Model.UserData;
import com.example.loginscreenapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.data.rdb.RdbStore;



public class LoginAbilitySlice extends AbilitySlice {

    public static final String PLEASE_ENTER_VALID_EMAIL_PASSWORD = "Please enter valid email & password.";
    public static final String LOGIN_SUCCESSFUL = "Login Successful";
    public static final String EMAIL_ID_NOT_FOUND = "Email Id not found.";
    public static final String PASSWORD_DOES_NOT_MATCH = "Password does not match.";
    public static final String USER_DATA = "UserData";
    TextField inputEmail;
    TextField inputPassword;

    Button loginButton;
    Text loginFailedErrorMessage;

    RdbStore db;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login_layout);

        inputEmail = (TextField)findComponentById(ResourceTable.Id_loginEmail);
        inputPassword = (TextField)findComponentById(ResourceTable.Id_loginPassword);

        loginButton = (Button)findComponentById(ResourceTable.Id_loginSubmitButton);
        loginFailedErrorMessage = (Text)findComponentById(ResourceTable.Id_loginFailedErrorMessage);
        loginFailedErrorMessage.setVisibility(Component.INVISIBLE);

        loginButton.setClickedListener(component -> {
            onClickSubmit();
        });
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

    public void onClickSubmit()
    {
        if (isLoginInputsValid())
        {
            LoginResponse loginResponse = tryLogin();
            if (!(loginResponse.getLoginResponseType() == LoginResponse.LoginResponseType.SUCCESS))
            {
                loginFailedErrorMessage.setVisibility(Component.VISIBLE);
                loginFailedErrorMessage.setText(getLoginErrorMessage(loginResponse.getLoginResponseType()));
            }
            else
            {
                Intent intent = new Intent();
                intent.setParam(USER_DATA, loginResponse.getUserData());
                present(new WelcomeAbilitySlice(), intent);
            }
        }
        else
        {
            loginFailedErrorMessage.setVisibility(Component.VISIBLE);
            loginFailedErrorMessage.setText(PLEASE_ENTER_VALID_EMAIL_PASSWORD);
        }
    }

    private boolean isLoginInputsValid()
    {
        String email = inputEmail.getText();
        if (isNullOrEmpty(email) || !InputValidationMethods.isValidEmail(email))
        {
            return false;
        }
        String password = inputPassword.getText();
        if (isNullOrEmpty(password) || !InputValidationMethods.isValidPassword(password))
        {
            return false;
        }
        return true;
    }
    private boolean isNullOrEmpty(String string)
    {
        return string == null || string.isEmpty();
    }
    private LoginResponse tryLogin()
    {
        LoginResponse.LoginResponseType loginResponseType = LoginResponse.LoginResponseType.SUCCESS;
        String email = inputEmail.getText();
        String password = inputPassword.getText();
        UserData userData = DbHelper.getUserDataIfExists(db, email);
        if (userData == null)
        {
            loginResponseType = LoginResponse.LoginResponseType.EMAIL_NOT_FOUND;
        }
        else
        {
            if (!password.equals(userData.getPassword()))
            {
                loginResponseType = LoginResponse.LoginResponseType.INCORRECT_PASSWORD;
                userData = null;
            }
        }
        return new LoginResponse(userData, loginResponseType);
    }

    private String getLoginErrorMessage(LoginResponse.LoginResponseType loginResponseType)
    {
        switch (loginResponseType)
        {
            case SUCCESS: return LOGIN_SUCCESSFUL;
            case EMAIL_NOT_FOUND: return EMAIL_ID_NOT_FOUND;
            case INCORRECT_PASSWORD: return PASSWORD_DOES_NOT_MATCH;
            default: return null;
        }
    }

}
