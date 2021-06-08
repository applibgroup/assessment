package com.example.assignment_app.slice;

import com.example.assignment_app.Data;
import com.example.assignment_app.ResourceTable;
import com.example.assignment_app.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
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
        if(intent.hasParameter(getString(ResourceTable.String_em))) {
            String s1= intent.getStringParam(getString(ResourceTable.String_em));
            String s2= intent.getStringParam(getString(ResourceTable.String_pas));
            email.setText(s1);
            password.setText(s2);
        }
        else {
            email.setText("");
            password.setText("");
        }
        Component toast_component=LayoutScatter.getInstance(Login.this).parse(ResourceTable.Layout_toast, null, false);
        Button login = (Button) findComponentById(ResourceTable.Id_login_btn);
        DatabaseHelper helper = new DatabaseHelper(Login.this);
        OrmContext context = helper.getOrmContext(getString(ResourceTable.String_alias), getString(ResourceTable.String_db_name), Data.class);
        Button signup = (Button) findComponentById(ResourceTable.Id_signup_btn);
        signup.setClickedListener(List -> present(new Signup(),new Intent()));
        login.setClickedListener(new Component.ClickedListener() {
             @Override
             public void onClick(Component component) {
                 if (email.getText().equals("") || password.getText().equals(""))
                 {
                     new ToastDialog(getContext()).setText(getString(ResourceTable.String_feild)).show();
                 }
                 else
                 {
                     OrmPredicates query = context.where(User.class).equalTo("DB_COLUMN_EMAIL", email.getText());
                     try {
                         List<User> users = context.query(query);
                         if (users.size()!=0 && password.getText().equals(users.get(0).getDB_COLUMN_PASSWORD()))
                         {
                            new ToastDialog(getContext()).setText("Login Success").show();
                            present(new Dashboard(),new Intent());
                         }
                         else if (users.size()!=0)
                         {
                             new ToastDialog(getContext()).setComponent(toast_component).show();
                         }
                         else
                         {
                             new ToastDialog(getContext()).setText(getString(ResourceTable.String_not_registered)).show();
                         }
                     }
                     catch (Exception e)
                     {

                        // Component toast_component=LayoutScatter.getInstance(Login.this).parse(ResourceTable.Layout_toast, null, false);
                         new ToastDialog(getContext()).setComponent(toast_component).show();
                         //present(new Login(),new Intent());.setText("did not matched")
                     }
                 }

             }
         });

    }
}
