package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.Info;
import com.devpatel10.loginsignup.ResourceTable;
import com.devpatel10.loginsignup.User;
import com.devpatel10.loginsignup.UserDB;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignUpAbilitySlice extends AbilitySlice {
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField password;
    private TextField mobile;
    private RadioContainer gender;

    private Text fName_error;
    private Text lName_error;
    private Text email_error;
    private Text password_error;
    private Text mobile_error;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
        DatabaseHelper helper = new DatabaseHelper(this);

        firstName=(TextField)findComponentById(ResourceTable.Id_firstname);
        lastName=(TextField)findComponentById(ResourceTable.Id_lastname);
        email=(TextField)findComponentById(ResourceTable.Id_email);
        password=(TextField)findComponentById(ResourceTable.Id_password);
        mobile=(TextField)findComponentById(ResourceTable.Id_mobile);
        gender=(RadioContainer) findComponentById(ResourceTable.Id_gender_container);


        fName_error=(Text)findComponentById(ResourceTable.Id_fname_error);
        lName_error=(Text)findComponentById(ResourceTable.Id_lname_error);
        email_error=(Text)findComponentById(ResourceTable.Id_email_error);
        password_error=(Text)findComponentById(ResourceTable.Id_password_error);
        mobile_error=(Text)findComponentById(ResourceTable.Id_mobile_error);

        Button submit=(Button)findComponentById(ResourceTable.Id_submit);
        submit.setClickedListener(component -> {
            if(check()){

                fName_error.setText("None");
                lName_error.setText("None");
                email_error.setText("None");
                password_error.setText("None");
                mobile_error.setText("None");

                OrmContext context = helper.getOrmContext("UserDB", "UserDB.db", UserDB.class);
                OrmPredicates query = context.where(User.class).equalTo("emailId",email.getText() );
                List<User> users = context.query(query);
                if(users.size()>0){
                    email_error.setText(ResourceTable.String_emailTaken);
                    email_error.setVisibility(Component.VISIBLE);
                }else{
                    User user = new User();
                    user.setFirstName(firstName.getText());
                    user.setLastName(lastName.getText());
                    user.setEmailId(email.getText());
                    user.setPassword(password.getText());
                    user.setMobileNo(mobile.getText());
                    if(gender.getMarkedButtonId()==1){
                        user.setGender("Male");
                    }else{
                        user.setGender("Female");
                    }
                    context.insert(user);
                    boolean isSuccess;
                    isSuccess = context.flush();

                    if(isSuccess) {
                        new ToastDialog(getContext())
                                .setText("Account Created Successfully!")
                                .show();
                        showDialogBox(component,user);

                    }
                    else {
                        new ToastDialog(getContext())
                                .setText("Oops Something went wrong!")
                                .show();
                        present(new SignUpAbilitySlice(),new Intent());
                    }
                }

            }
            else present(new SignUpAbilitySlice(),new Intent());
        });
    }

    private boolean check() {
        fName_error.setVisibility(Component.INVISIBLE);
        lName_error.setVisibility(Component.INVISIBLE);
        email_error.setVisibility(Component.INVISIBLE);
        password_error.setVisibility(Component.INVISIBLE);
        mobile_error.setVisibility(Component.INVISIBLE);
        boolean ans=true;
        if(firstName.getText().equals(""))
        {
            fName_error.setText(ResourceTable.String_emptyFirstName);
            fName_error.setVisibility(Component.VISIBLE);
            ans=false;
        }else if(!Pattern.matches("[a-zA-z]+",firstName.getText())){
            fName_error.setText(ResourceTable.String_firstNameInvalid);
            fName_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        if(lastName.getText().equals(""))
        {
            lName_error.setText(ResourceTable.String_emptyLastName);
            lName_error.setVisibility(Component.VISIBLE);
            ans=false;
        }else if(!Pattern.matches("[a-zA-z]+",lastName.getText())){
            lName_error.setText(ResourceTable.String_lastNameInvalid);
            lName_error.setVisibility(Component.VISIBLE);
            ans=false;
        }
        String emailRegEx ="^[a-zA-Z0-9+_.-]+@+[a-zA-Z0-9-]+.+[a-zA-Z0-9-]+$";
        Pattern epat=Pattern.compile(emailRegEx);
        if(email.getText().equals(""))
        {
            email_error.setText(ResourceTable.String_emptyEmail);
            email_error.setVisibility(Component.VISIBLE);
            ans=false;
        }else if(!epat.matcher(email.getText()).matches()){
            email_error.setText(ResourceTable.String_emailInvalid);
            email_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        if(password.getText().equals(""))
        {
            password_error.setText(ResourceTable.String_emptyPassword);
            password_error.setVisibility(Component.VISIBLE);
            ans=false;
        }
        else if(password.getText().length()<5)
        {
            password_error.setText(ResourceTable.String_passwordInvalid);
            password_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        Pattern p = Pattern.compile("^[7-9][0-9]{9}$");
        if(mobile.getText().equals(""))
        {
            mobile_error.setText(ResourceTable.String_emptyMobile);
            mobile_error.setVisibility(Component.VISIBLE);
            ans=false;
        }
        else if(!p.matcher(mobile.getText()).matches())
        {
            mobile_error.setText(ResourceTable.String_mobileInvalid);
            mobile_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        return ans;
    }
    public void showDialogBox(Component component, User user) {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText(component.getContext().getString(ResourceTable.String_signup_dialog_title));
        commonDialog.setContentText(component.getContext().getString(ResourceTable.String_signup_dialog_content));
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(MATCH_CONTENT, MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1,
                component.getContext().getString(ResourceTable.String_signup_to_login),
                (iDialog, i) -> {
                    Intent intent = new Intent();
                    intent.setParam(Info.EMAIL,user.getEmailId());
                    intent.setParam(Info.PASSWORD,user.getPassword());
                    present(new LoginAbilitySlice(), intent);

                    iDialog.destroy();
                });
        commonDialog.setButton(IDialog.BUTTON2,
                component.getContext().getString(ResourceTable.String_signup_cancel_login),
                (iDialog, i) -> {
                    iDialog.hide();
                });
        commonDialog.show();
    }
    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
