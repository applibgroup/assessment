package com.example.basic.slice;

import com.example.basic.ResourceTable;
import com.example.basic.StoreDb;
import com.example.basic.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpSlice extends AbilitySlice {

    TextField first_name, last_name, email, password, mobile;
    RadioButton radioButton_male, radioButton_female;
    Button signUp;
    Text signup_success;
    static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

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


    public void insert(OrmContext ormContext, User user){
        boolean isSuccess = ormContext.insert(user);
        isSuccess = ormContext.flush();

        if(isSuccess){
            signup_success.setVisibility(Component.VISIBLE);
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
        boolean isCorrectName = matcher.matches();
        if(isCorrectName){
            signUp.setEnabled(true);
        }
        else {
            signUp.setEnabled(false);
        }
        return isCorrectName;
    }

    private boolean correctEmail(String str){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean isCorrectEmail = matcher.matches();
        if(isCorrectEmail){
            signUp.setEnabled(true);
        }
        else{
            signUp.setEnabled(false);
        }
        return isCorrectEmail;
    }

    private boolean correctPassword(String str){
        if(str.length()>=6){
            signUp.setEnabled(true);
            return true;
        }
        signUp.setEnabled(false);
        return false;
    }

    private boolean correctPhone(String str){
        signUp.setEnabled(false);
        if(str.length()!=10)
            return false;
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(!(curr<='9'&&curr>='0'))
                return false;
        }
        signUp.setEnabled(true);
        return true;
    }

    private interface Lambda{
        boolean checkTextField(String s);
    }

    public void setObserver(int textFieldID, int errTextID, Lambda l)
    {
        TextField textField = (TextField) findComponentById(textFieldID);
        Text text = (Text) findComponentById(errTextID);
        Text.TextObserver observer = new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if (!l.checkTextField(textField.getText()))
                    text.setVisibility(Component.VISIBLE);
                else
                    text.setVisibility(Component.HIDE);
            }
        };
        textField.addTextObserver(observer);
    }
}
