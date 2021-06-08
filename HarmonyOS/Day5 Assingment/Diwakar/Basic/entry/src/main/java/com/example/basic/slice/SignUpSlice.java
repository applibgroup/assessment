package com.example.basic.slice;

import com.example.basic.ResourceTable;
import com.example.basic.StoreDb;
import com.example.basic.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignUpSlice extends AbilitySlice {

    TextField first_name, last_name, email, password, mobile;
    RadioButton radioButton_male, radioButton_female;
    Button signUp;
    Text signup_success;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_sign_up);
        setObserver(ResourceTable.Id_first_name, ResourceTable.Id_first_name_err, this::correctName);
        setObserver(ResourceTable.Id_last_name, ResourceTable.Id_last_name_err, this::correctName);
        setObserver(ResourceTable.Id_email, ResourceTable.Id_email_err, this::correctEmail);
        setObserver(ResourceTable.Id_password, ResourceTable.Id_password_err, this::correctPassword);
        setObserver(ResourceTable.Id_mobile, ResourceTable.Id_mobile_err, this::correctPhone);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        OrmContext ormContext = dbHelper.getOrmContext("StoreDb","storeDb.db", StoreDb.class);

        RadioContainer radioContainer;

        first_name = (TextField)findComponentById(ResourceTable.Id_first_name);
        last_name = (TextField)findComponentById(ResourceTable.Id_last_name);
        email = (TextField)findComponentById(ResourceTable.Id_email);
        password = (TextField)findComponentById(ResourceTable.Id_password);
        mobile = (TextField)findComponentById(ResourceTable.Id_mobile);
        signUp = (Button)findComponentById(ResourceTable.Id_signup);
        radioContainer = (RadioContainer)findComponentById(ResourceTable.Id_gender);
        signup_success = (Text)findComponentById(ResourceTable.Id_signup_success);
        radioButton_male = (RadioButton)findComponentById(ResourceTable.Id_gender_male);
        radioButton_female = (RadioButton)findComponentById(ResourceTable.Id_gender_female);

        signUp.setClickedListener(component -> {
            User user = new User();
            user.setFirst_name(first_name.getText());
            user.setLast_name(last_name.getText());
            user.setEmail(email.getText());
            user.setPassword(password.getText());
            user.setMobile(mobile.getText());

            switch (radioContainer.getMarkedButtonId()){
                case 0:
                    user.setGender("Male");
                    insert(ormContext,user);
                    break;
                case 1:
                    user.setGender("Female");
                    insert(ormContext,user);
                    break;
                default:
                    Text gender_err = (Text)findComponentById(ResourceTable.Id_gender_err);
                    gender_err.setVisibility(Component.VISIBLE);
                    break;
            }
        });
    }


    private void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("Signup Success !!");
        commonDialog.setContentText("Go to Login Page to enter the App.");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(1000, MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
            iDialog.destroy();
            Intent intent = new Intent();
            intent.setParam("email", email.getText());
            intent.setParam("password",password.getText());
            present(new LoginSlice(),intent);

        });
        commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
            iDialog.destroy();
        });
        commonDialog.show();
    }

    private void showToast(String msg) {
        new ToastDialog(this).setText(msg).setDuration(2000).show();
    }


    public void insert(OrmContext ormContext, User user){
        if(correctName(first_name.getText()) && correctName(last_name.getText()) && correctEmail(email.getText()) && correctPhone(mobile.getText()) && correctPassword(password.getText())){
            boolean isSuccess = ormContext.insert(user);
            isSuccess = ormContext.flush();
            showCommonDialog();
        }
        else{
            showToast("Invalid Details!!");
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private boolean correctName(String str){
        String regex = "[A-Za-z.]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean correctEmail(String str){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
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

    private interface Lambda{
        boolean checkTextField(String s);
    }

    public void setObserver(int textFieldID, int errTextID, Lambda l)
    {
        TextField textField = (TextField) findComponentById(textFieldID);
        Text text = (Text) findComponentById(errTextID);
        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (!l.checkTextField(textField.getText()))
                text.setVisibility(Component.VISIBLE);
            else
                text.setVisibility(Component.INVISIBLE);
        };
        textField.addTextObserver(observer);
    }
}
