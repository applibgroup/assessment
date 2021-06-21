package com.example.basic.slice;

import com.example.basic.ResourceTable;
import com.example.basic.StoreDb;
import com.example.basic.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.utils.Color;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSlice extends AbilitySlice {

    Button login;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        setObserver(ResourceTable.Id_email_login, ResourceTable.Id_email_err_login, this::correctEmail);

        TextField email, password;
        Text login_message;

        email = (TextField)findComponentById(ResourceTable.Id_email_login);
        password = (TextField)findComponentById(ResourceTable.Id_password_login);
        login = (Button)findComponentById(ResourceTable.Id_login);
        login_message = (Text)findComponentById(ResourceTable.Id_login_message);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        OrmContext ormContext = dbHelper.getOrmContext("StoreDb","storeDb.db", StoreDb.class);


        login.setClickedListener(component -> {
            OrmPredicates predicates = new OrmPredicates(User.class);
            predicates.equalTo("email", email.getText());
            predicates.equalTo("password", password.getText());
            List<User> users = ormContext.query(predicates);
            if(users.size()==0){
                login_message.setText("LOGIN FAILED");
                login_message.setTextColor(Color.RED);
            }
            else{
                login_message.setText("LOGIN SUCCESS");
                login_message.setTextColor(Color.GREEN);
            }
            email.setText("");
            password.setText("");
            Text email_err = (Text)findComponentById(ResourceTable.Id_email_err_login);
            email_err.setVisibility(Component.INVISIBLE);
        });


    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private boolean correctEmail(String str){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean isCorrectEmail = matcher.matches();
        if(isCorrectEmail){
            login.setEnabled(true);
        }
        else{
            login.setEnabled(false);
        }
        return isCorrectEmail;
    }

    private interface Lambda{
        boolean checkTextField(String s);
    }

    public void setObserver(int textFieldID, int errTextID, LoginSlice.Lambda l)
    {
        TextField textField = (TextField) findComponentById(textFieldID);
        Text text = (Text) findComponentById(errTextID);
        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (!l.checkTextField(textField.getText()))
                text.setVisibility(Component.VISIBLE);
            else
                text.setVisibility(Component.HIDE);
        };
        textField.addTextObserver(observer);
    }
}
