package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import com.example.loginapp.StoreDb;
import com.example.loginapp.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupAbilitySlice extends AbilitySlice {
    OrmContext ormContext;
    private static String email_val;
    private static String password_val;
    TextField first_name;
    TextField last_name;
    TextField email;
    TextField password;
    TextField phone;
    RadioButton rb_male;
    RadioButton rb_female;
    RadioContainer rb_container;

    public static String getEmail_val() {
        return email_val;
    }

    public static String getPassword_val(){
        return password_val;
    }

    private interface Lambda{
        boolean checkTextField(String s);
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        setObserver(ResourceTable.Id_first_name, ResourceTable.Id_first_name_error, this::correctName);
        setObserver(ResourceTable.Id_last_name, ResourceTable.Id_last_name_error, this::correctName);
        setObserver(ResourceTable.Id_email, ResourceTable.Id_email_error, this::correctEmail);
        setObserver(ResourceTable.Id_password, ResourceTable.Id_password_error, this::correctPassword);
        setObserver(ResourceTable.Id_phone, ResourceTable.Id_phone_error, this::correctPhone);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        ormContext = dbHelper.getOrmContext("StoreDb","storeDb.db", StoreDb.class);
        Button signup_btn = (Button) findComponentById(ResourceTable.Id_sign_up_btn);

        first_name = (TextField) findComponentById(ResourceTable.Id_first_name);
        last_name = (TextField) findComponentById(ResourceTable.Id_last_name);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        phone = (TextField) findComponentById(ResourceTable.Id_phone);
        rb_male = (RadioButton) findComponentById(ResourceTable.Id_rb_male);
        rb_female = (RadioButton) findComponentById(ResourceTable.Id_rb_female);
        rb_container = (RadioContainer) findComponentById(ResourceTable.Id_rb_container);

        signup_btn.setClickedListener((component-> {

            if(validateAll()) {
                User user = new User();
                user.setFirst_name(first_name.getText());
                user.setLast_name(last_name.getText());
                user.setEmail(email.getText());
                user.setPassword(password.getText());
                user.setPhone(phone.getText());
                if (rb_male.isSelected())
                    user.setGender('M');
                else
                    user.setGender('F');
                insert(user);
                showCommonDialog();
            }
            else{
                showToast("Invalid or empty input");
            }
        }));

    }

    public boolean insert(User user){
        boolean isSuccess1 = ormContext.insert(user);
        boolean isSuccess2 = ormContext.flush();
        return isSuccess1 && isSuccess2;
    }

    private boolean correctName(String str){
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(!(curr>='A' && curr<='Z')&&!(curr>='a' && curr<='z')&&(curr!=' ')&&(curr!='.')){
                return false;
            }
        }
        return true;
    }

    private boolean correctEmail(String str){
        int dotafterat = 0;
        int at = 0;
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(curr==' '){
                return false;
            }
            if(curr=='@'){
                at += 1;
                dotafterat = 0;
            }
            if(curr=='.'){
                if(at>0) dotafterat += 1;
            }
        }
        return at == 1 && dotafterat == 1;
    }

    private boolean correctPassword(String str){
        return str.length() >= 6;
    }

    private boolean correctPhone(String str){
        if(str.length()!=10)
            return false;
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(!(curr<='9'&&curr>='0'))
                return false;
        }
        return true;
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    public void setObserver(int textFieldID, int errTextID, Lambda l)
    {
        TextField textField = (TextField) findComponentById(textFieldID);
        Text text = (Text) findComponentById(errTextID);
        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (!l.checkTextField(textField.getText()))
            {
                text.setVisibility(Component.VISIBLE);
            }
            else {
                text.setVisibility(Component.HIDE);
            }

        };
        textField.addTextObserver(observer);
    }

    private void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("Sign Up Successful!");
        commonDialog.setContentText("Go to Login Page to enter the app. Please confirm");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(MATCH_CONTENT,MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1, "Yes", ((iDialog, i) -> {
            TextField email = (TextField) findComponentById(ResourceTable.Id_email);
            TextField password = (TextField) findComponentById(ResourceTable.Id_password);
            email_val = email.getText();
            password_val = password.getText();
            present(new LoginAbilitySlice(), new Intent());
            iDialog.destroy();
        }));
        commonDialog.setButton(IDialog.BUTTON2, "No", ((iDialog, i) -> iDialog.destroy()));
        commonDialog.show();
    }

    private void showToast(String msg) {
        new ToastDialog(this).setText(msg).setDuration(1000).show();
    }

    private boolean validateAll() {
        boolean cond1 = correctName(first_name.getText()) && (first_name.getText().length()>0);
        boolean cond2 = correctName(last_name.getText()) && (last_name.getText().length()>0);
        boolean cond3 = correctEmail(email.getText()) && (email.getText().length()>0);
        boolean cond4 = correctPassword(password.getText()) && (password.getText().length()>0);
        boolean cond5 = correctPhone(phone.getText()) && (phone.getText().length()>0);
        boolean cond6 = (rb_container.getMarkedButtonId()!=RadioContainer.INVALID_ID);
        return cond1 && cond2 && cond3 && cond4 && cond5 && cond6;
    }
}
