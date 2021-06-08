package com.example.loginscreenapplication.Model;


public class LoginResponse{
    private final UserData userData;
    private final LoginResponseType loginResponseType;

    public LoginResponse(UserData userData, LoginResponseType loginResponseType)
    {
        this.userData = userData;
        this.loginResponseType = loginResponseType;
    }

    public UserData getUserData() {
        return userData;
    }

    public LoginResponseType getLoginResponseType() {
        return loginResponseType;
    }

    public enum LoginResponseType {
        SUCCESS,
        EMAIL_NOT_FOUND,
        INCORRECT_PASSWORD
    }
}
