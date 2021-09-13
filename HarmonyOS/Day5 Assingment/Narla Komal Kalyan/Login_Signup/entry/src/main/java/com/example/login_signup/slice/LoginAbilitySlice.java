package com.example.login_signup.slice;

import com.example.login_signup.MYDB;
import com.example.login_signup.ResourceTable;
import com.example.login_signup.User;
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

public class LoginAbilitySlice extends AbilitySlice {
    private TextField email;
    private TextField password;

    private Text email_error;
    private Text password_error;

    private String email_str;
    private String password_str;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        DatabaseHelper helper = new DatabaseHelper(this);

        email = (TextField) findComponentById(ResourceTable.Id_login_email);
        password = (TextField) findComponentById(ResourceTable.Id_login_password);

        email_error = (Text) findComponentById(ResourceTable.Id_login_email_error);
        password_error = (Text) findComponentById(ResourceTable.Id_login_password_error);

        email.setText(intent.getStringParam("email"));
        password.setText(intent.getStringParam("password"));

        Button login = (Button)findComponentById(ResourceTable.Id_login);
        login.setClickedListener(component -> {
            email_str = email.getText();
            password_str = password.getText();

            email_error.setText("None");
            password_error.setText("None");

            if(simplecheck()) {
                OrmContext context = helper.getOrmContext("MYDB", "MYDB.db", MYDB.class);
                OrmPredicates query = context.where(User.class).equalTo("email", email_str).and().equalTo("password", password_str);
                List<User> users = context.query(query);
                if(users.size()==1) {
                    new ToastDialog(getContext())
                            .setText("Login Successful!")
                            .show();
                    present(new WelcomeAbilitySlice(),new Intent());
                }
                else {
                    password_error.setText("Email or Password are Incorrect");
                    password_error.setVisibility(Component.VISIBLE);
                }
            }
            else {
                new ToastDialog(getContext())
                    .setText("Please specify valid details!")
                    .show();
            }
        });
    }

    public boolean simplecheck() {
        boolean answer = true;

        if(email_str.equals(""))
            email_error.setText("Specify Correct email");

        if(password_str.equals(""))
            password_error.setText("Password can't be empty");

        if(email_error.getText().equals("None"))
            email_error.setVisibility(Component.INVISIBLE);
        else {
            email_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        if(password_error.getText().equals("None"))
            password_error.setVisibility(Component.INVISIBLE);
        else {
            password_error.setVisibility(Component.VISIBLE);
            answer = false;
        }
        return answer;
    }
}