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
import ohos.agp.window.dialog.ToastDialog;
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

        email = (TextField)findComponentById(ResourceTable.Id_email_login);
        password = (TextField)findComponentById(ResourceTable.Id_password_login);
        login = (Button)findComponentById(ResourceTable.Id_login);

        if(intent.hasParameter("email") && intent.hasParameter("password")){
            String email_ = intent.getStringParam("email");
            String password_ = intent.getStringParam("password");
            email.setText(email_);
            password.setText(password_);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        OrmContext ormContext = dbHelper.getOrmContext("StoreDb","storeDb.db", StoreDb.class);


        login.setClickedListener(component -> {
            OrmPredicates predicates = new OrmPredicates(User.class);
            predicates.equalTo("email", email.getText());
            predicates.equalTo("password", password.getText());
            List<User> users = ormContext.query(predicates);
            if(users.size()==0){
                showToast("Invalid Credentials!!");
            }
            else{
                showToast("Login Successful!!");
                present(new ProfileSlice(),new Intent());
            }
            email.setText("");
            password.setText("");
            Text email_err = (Text)findComponentById(ResourceTable.Id_email_err_login);
            email_err.setVisibility(Component.INVISIBLE);
        });
    }
    private void showToast(String msg) {
        new ToastDialog(this).setText(msg).setDuration(2000).show();
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
        return matcher.matches();
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
                text.setVisibility(Component.INVISIBLE);
        };
        textField.addTextObserver(observer);
    }
}
