package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {

    private TextField firstName;
    private TextField lastName;
    private TextField password;
    private TextField email;
    private TextField mobile;
    private Checkbox genderMale;
    private Checkbox genderFemale;
    private Button signupBtn2;
    private Text firstNameText;
    private Text lastNameText;
    private Text emailText;
    private Text passwordText;
    private Text mobileText;
    private String firstNameString;
    private String lastNameString;
    private String passwordString;
    private String emailString;
    private String mobileString;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        firstName = (TextField) findComponentById(ResourceTable.Id_firstname_textField);
        firstNameText = (Text) findComponentById(ResourceTable.Id_firstname_error);

        lastName = (TextField) findComponentById(ResourceTable.Id_lastname_textField);
        lastNameText = (Text) findComponentById(ResourceTable.Id_lastname_error);

        password = (TextField) findComponentById(ResourceTable.Id_password_textField);
        passwordText = (Text) findComponentById(ResourceTable.Id_password_error);

        email = (TextField) findComponentById(ResourceTable.Id_email_textField);
        emailText = (Text) findComponentById(ResourceTable.Id_email_error);

        mobile = (TextField) findComponentById(ResourceTable.Id_mobile_textField);
        mobileText = (Text) findComponentById(ResourceTable.Id_mobile_error);

        genderMale = (Checkbox) findComponentById(ResourceTable.Id_male_check_box);
        genderFemale = (Checkbox) findComponentById(ResourceTable.Id_female_check_box);

        signupBtn2 = (Button) findComponentById(ResourceTable.Id_signup_button2);
        signupBtn2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                getData();
                if(checkData()){
                    //data management
                }
            }
        });
    }

    public void getData(){
        firstNameString = firstName.getText();
        lastNameString = lastName.getText();
        passwordString = password.getText();
        emailString = email.getText();
        mobileString = mobile.getText();
    }

    public Boolean checkData(){
        boolean bool = true;
        if(!checkName(firstNameString)){
            firstNameText.setVisibility(Component.VISIBLE);
            bool = false;
        } else firstNameText.setVisibility(Component.HIDE);
        if(!checkName(lastNameString)){
            lastNameText.setVisibility(Component.VISIBLE);
            bool = false;
        } else lastNameText.setVisibility(Component.HIDE);
        if(!checkPassword()){
            passwordText.setVisibility(Component.VISIBLE);
            bool = false;
        }else passwordText.setVisibility(Component.HIDE);
        if(!checkEmail()){
            emailText.setVisibility(Component.VISIBLE);
            bool = false;
        }else emailText.setVisibility(Component.HIDE);
        if(!checkMobile()){
            mobileText.setVisibility(Component.VISIBLE);
            bool = false;
        }else mobileText.setVisibility(Component.HIDE);
        return bool;
    }

    public Boolean checkName(String Name){ return Pattern.matches("[a-zA-Z]+",Name); }

    public Boolean checkPassword(){
        return passwordString.length() >= 5;
    }

    public Boolean checkEmail(){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailString);
        return matcher.find();
    }

    public Boolean checkMobile(){
        try{
            Integer.parseInt(mobileString);
            return true;
        }
        catch (Exception E){
            return false;
        }
    }
}
