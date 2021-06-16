package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.database.User;
import com.example.myapplication.database.UserInfo;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

import static com.example.myapplication.utils.CheckingDataUtils.*;

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
    private Text emailExistsText;
    private Text passwordText;
    private Text mobileText;
    private Text genderText;
    private String firstNameString;
    private String lastNameString;
    private String passwordString;
    private String emailString;
    private String mobileString;
    private String genderString;
    OrmPredicates query;
    DatabaseHelper helper;
    OrmContext context;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        helper = new DatabaseHelper(SignupAbilitySlice.this);
        context = helper.getOrmContext("UserInfo", "UserInfo.db", UserInfo.class);

        firstName = (TextField) findComponentById(ResourceTable.Id_firstname_textField);
        firstNameText = (Text) findComponentById(ResourceTable.Id_firstname_error);

        lastName = (TextField) findComponentById(ResourceTable.Id_lastname_textField);
        lastNameText = (Text) findComponentById(ResourceTable.Id_lastname_error);

        password = (TextField) findComponentById(ResourceTable.Id_password_textField);
        passwordText = (Text) findComponentById(ResourceTable.Id_password_error);

        email = (TextField) findComponentById(ResourceTable.Id_email_textField);
        emailText = (Text) findComponentById(ResourceTable.Id_email_error);
        emailExistsText = (Text) findComponentById(ResourceTable.Id_email_exists_error);

        mobile = (TextField) findComponentById(ResourceTable.Id_mobile_textField);
        mobileText = (Text) findComponentById(ResourceTable.Id_mobile_error);

        genderMale = (Checkbox) findComponentById(ResourceTable.Id_male_check_box);
        genderFemale = (Checkbox) findComponentById(ResourceTable.Id_female_check_box);
        genderText = (Text) findComponentById(ResourceTable.Id_gender_error);
        genderString = "";

        signupBtn2 = (Button) findComponentById(ResourceTable.Id_signup_button2);
        signupBtn2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                getData();
                if(checkData()){
                    //data management
                    query = context.where(User.class).equalTo("email", emailString);
                    List<User> users = context.query(query);
                    if(users.size() > 0){
                        emailExistsText.setVisibility(Component.VISIBLE);
                    }else {
                        emailExistsText.setVisibility(Component.HIDE);
                        User user = new User();
                        user.setFirstName(firstNameString);
                        user.setLastName(lastNameString);
                        user.setEmail(emailString);
                        user.setPassword(passwordString);
                        user.setMobile(mobileString);
                        user.setGender(genderString);
                        boolean isSuccessed = context.insert(user);
                        isSuccessed = context.flush();
                        if (isSuccessed) {
                            present(new HomeAbilitySlice(), new Intent());
                        }
                    }
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
        if(genderMale.isChecked() && genderFemale.isChecked()) genderString = "";
        else if(genderFemale.isChecked()) genderString = "female";
        else if(genderMale.isChecked()) genderString = "male";
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
        if(!checkPassword(passwordString)){
            passwordText.setVisibility(Component.VISIBLE);
            bool = false;
        }else passwordText.setVisibility(Component.HIDE);
        if(!checkEmail(emailString)){
            emailText.setVisibility(Component.VISIBLE);
            bool = false;
        }else emailText.setVisibility(Component.HIDE);
        if(!checkMobile(mobileString)){
            mobileText.setVisibility(Component.VISIBLE);
            bool = false;
        }else mobileText.setVisibility(Component.HIDE);
        if(genderString.length() == 0){
            genderText.setVisibility(Component.VISIBLE);
            bool = false;
        }else genderText.setVisibility(Component.HIDE);
        return bool;
    }
}
