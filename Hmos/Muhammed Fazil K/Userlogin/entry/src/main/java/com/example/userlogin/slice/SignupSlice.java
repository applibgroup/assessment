package com.example.userlogin.slice;

import com.example.userlogin.data.User;
import com.example.userlogin.data.UserDatabase;
import com.example.userlogin.ResourceTable;
import com.example.userlogin.Utils.Constants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupSlice extends AbilitySlice {

    public static final int DIALOG_BOX_WIDTH = 984;

    OrmContext ormContext;

    TextField firstNameField,lastNameField,emailField,passwordField,mobileField;
    Text firstNameError,lastNameError,emailError,passwordError,mobileError,genderError;
    RadioButton maleBtn,femaleBtn;
    Button signupBtn;

    Boolean error=true;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup_page);
        intiViews();
        setListeners();
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        ormContext=databaseHelper.getOrmContext(Constants.DB_ALIAS,Constants.DB_NAME, UserDatabase.class);

    }



    private void intiViews() {

        firstNameField=(TextField)findComponentById(ResourceTable.Id_signup_firstname);
        lastNameField=(TextField)findComponentById(ResourceTable.Id_signup_lastname);
        emailField=(TextField)findComponentById(ResourceTable.Id_signup_email);
        passwordField=(TextField)findComponentById(ResourceTable.Id_signup_password);
        mobileField=(TextField)findComponentById(ResourceTable.Id_signup_mobile);

        firstNameError=(Text) findComponentById(ResourceTable.Id_signuperror_firstname);
        firstNameError.setVisibility(Component.INVISIBLE);
        lastNameError=(Text) findComponentById(ResourceTable.Id_signuperror_lastname);
        lastNameError.setVisibility(Component.INVISIBLE);
        emailError=(Text) findComponentById(ResourceTable.Id_signuperror_email);
        emailError.setVisibility(Component.INVISIBLE);
        passwordError=(Text) findComponentById(ResourceTable.Id_signuperror_password);
        passwordError.setVisibility(Component.INVISIBLE);
        mobileError=(Text) findComponentById(ResourceTable.Id_signuperror_mobile);
        mobileError.setVisibility(Component.INVISIBLE);
        genderError=(Text) findComponentById(ResourceTable.Id_signuperror_gender);
        genderError.setVisibility(Component.INVISIBLE);

        maleBtn=(RadioButton)findComponentById(ResourceTable.Id_male_btn) ;
        femaleBtn=(RadioButton)findComponentById(ResourceTable.Id_female_btn) ;

        signupBtn=(Button)findComponentById(ResourceTable.Id_final_signupbtn);

    }
    private void setListeners() {

        firstNameField.addTextObserver((s, i, i1, i2) -> checkError(s,firstNameError, Constants.FIRSTNAMEPATTERN));

        lastNameField.addTextObserver((s, i, i1, i2) -> checkError(s,lastNameError,Constants.LASTNAMEPATTERN));

        emailField.addTextObserver((s, i, i1, i2) -> checkError(s,emailError,Constants.EMAILPATTERN));

        passwordField.addTextObserver((s, i, i1, i2) -> {
            if(s.length()<5){
                error=true;
                passwordError.setText("Enter a strong password");
                passwordError.setVisibility(Component.VISIBLE);
            }else{
                error=false;
                passwordError.setVisibility(Component.INVISIBLE);
            }
        });

        mobileField.addTextObserver((s, i, i1, i2) -> checkError(s,mobileError,Constants.MOBILEPATTERN));

        if(maleBtn.isChecked()||femaleBtn.isChecked()){
            error=false;
            genderError.setVisibility(Component.INVISIBLE);

        }
        else {
            error=true;
            genderError.setText("Please choose a gender");
            genderError.setVisibility(Component.VISIBLE);

        }

        signupBtn.setClickedListener(component -> registerUser());

    }

    private void registerUser() {
        if(!error){
            String gender;
            if(maleBtn.isChecked())
                gender="male";
            else
                gender="female";

            User user=new User();
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(passwordField.getText());
            user.setMobile(mobileField.getText());
            user.setGender(gender);
            if(!userExist(user)){
                ormContext.insert(user);
                ormContext.flush();
                new ToastDialog(this).setText("User Inserted Successfully");
                gotoLogin();

            }



        }

    }

    private void gotoLogin() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("Sign in Successfull");
        commonDialog.setContentText("Go to login Page");
        commonDialog.setAutoClosable(false);
        commonDialog.setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
            Intent intent = new Intent();
            intent.setParam(Constants.EMAIL_COLUMN, emailField.getText());
            intent.setParam(Constants.PASSWORD_COLUMN, passwordField.getText());
            present(new LoginSlice(), intent);
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
            iDialog.destroy();
        });
        commonDialog.show();
    }


    private boolean userExist(User user) {
        OrmPredicates ormPredicates=ormContext.where(User.class);
        ormPredicates.equalTo(Constants.EMAIL_COLUMN,user.getEmail());
        List<User> users= ormContext.query(ormPredicates);
        if(users.size()==0 || users==null){
            new ToastDialog(this).setText("User Not  Exist").setDuration(1000).show();
            return false;
        }

        else
        {
            new ToastDialog(this).setText("User Already Exist").setDuration(1000).show();
            return true;
        }

    }


    private void checkError(String s, Text errEmail, String pattern) {
        boolean matched = s.matches(pattern);
        if (s.isEmpty()) {
            errEmail.setText("It is Empty");
            errEmail.setVisibility(Component.VISIBLE);
            error = true;
        } else if (matched) {
            errEmail.setVisibility(Component.INVISIBLE);
            error = false;
        } else {
            errEmail.setText("Enter Valid data");
            errEmail.setVisibility(Component.VISIBLE);
            error = true;
        }
    }
}
