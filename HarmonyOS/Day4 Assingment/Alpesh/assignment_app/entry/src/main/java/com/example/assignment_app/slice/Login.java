package com.example.assignment_app.slice;

import com.example.assignment_app.Data;
import com.example.assignment_app.ResourceTable;
import com.example.assignment_app.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class Login extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);
        TextField email = (TextField) findComponentById(ResourceTable.Id_email_);
        TextField password = (TextField) findComponentById(ResourceTable.Id_password_);
        email.setText("");
        password.setText("");
        Button login = (Button) findComponentById(ResourceTable.Id_login_btn);
        DatabaseHelper helper = new DatabaseHelper(Login.this);
        OrmContext context = helper.getOrmContext(getString(ResourceTable.String_alias), getString(ResourceTable.String_db_name), Data.class);
        Button signup = (Button) findComponentById(ResourceTable.Id_signup_btn);
        signup.setClickedListener(List -> present(new Signup(),new Intent()));
        login.setClickedListener(new Component.ClickedListener() {
             @Override
             public void onClick(Component component) {
                 OrmPredicates query = context.where(User.class).equalTo("DB_COLUMN_EMAIL", email.getText());
                 try {
                     List<User> users = context.query(query);
                     if (password.getText().equals(users.get(0).getDB_COLUMN_PASSWORD()))
                     {
                        present(new Dashboard(),new Intent());
                     }
                     else if (users.isEmpty())
                     {
                         new ToastDialog(getContext()).setText("No match").show();
                     }
                     else
                     {
                         new ToastDialog(getContext()).setText("Email or Password not matched").show();
                     }
                 }
                 catch (Exception e)
                 {
                     new ToastDialog(getContext()).setText("Email or Password did not matched").show();
                     //present(new Login(),new Intent());
                 }

             }
         });

    }
}
