package com.devpatel10.loginsignup.slice;

import com.devpatel10.loginsignup.ResourceTable;
import com.devpatel10.loginsignup.User;
import com.devpatel10.loginsignup.UserDB;
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
    private TextField loginEmail;
    private TextField loginPassword;

    private Text login_email_error;
    private Text login_password_error;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        DatabaseHelper helper = new DatabaseHelper(this);

        loginEmail = (TextField) findComponentById(ResourceTable.Id_login_email);
        loginPassword = (TextField) findComponentById(ResourceTable.Id_login_password);

        login_email_error = (Text) findComponentById(ResourceTable.Id_login_email_error);
        login_password_error = (Text) findComponentById(ResourceTable.Id_login_password_error);
        Button login = (Button)findComponentById(ResourceTable.Id_loginBtn);
        login.setClickedListener(component -> {
            login_email_error.setText("None");
            login_password_error.setText("None");
            if(check()){
                OrmContext context = helper.getOrmContext("UserDB", "UserDB.db", UserDB.class);
                OrmPredicates query = context.where(User.class).equalTo("emailId", loginEmail.getText()).and().equalTo("password", loginPassword.getText());
                List<User> users = context.query(query);
                if(users.size()!=0) {
                    new ToastDialog(getContext())
                            .setText("Login Successful!")
                            .show();
                    present(new WelcomeAbilitySlice(),new Intent());
                }
                else {
                    new ToastDialog(getContext())
                            .setText("Email Id or Password are incorrect!")
                            .show();
                }

            }
        });
    }

    private boolean check() {
        boolean ans=true;

        if(loginEmail.getText().equals(""))
        {
            login_email_error.setText(ResourceTable.String_emptyEmail);
            login_email_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        if(loginPassword.getText().equals(""))
        {
            login_password_error.setText(ResourceTable.String_emptyPassword);
            login_password_error.setVisibility(Component.VISIBLE);
            ans=false;
        }

        return ans;
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
