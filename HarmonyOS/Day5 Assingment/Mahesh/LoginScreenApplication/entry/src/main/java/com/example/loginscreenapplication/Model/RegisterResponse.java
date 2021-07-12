package com.example.loginscreenapplication.Model;

public class RegisterResponse {
    private final UserData userData;
    private final RegisterResponse.RegisterResponseType registerResponseType;

    public RegisterResponse(UserData userData, RegisterResponse.RegisterResponseType registerResponseType)
    {
        this.userData = userData;
        this.registerResponseType = registerResponseType;
    }

    public UserData getUserData() {
        return userData;
    }

    public RegisterResponse.RegisterResponseType getRegisterResponseType() {
        return registerResponseType;
    }

    public enum RegisterResponseType {
        SUCCESS,
        FAIL
    }
}

