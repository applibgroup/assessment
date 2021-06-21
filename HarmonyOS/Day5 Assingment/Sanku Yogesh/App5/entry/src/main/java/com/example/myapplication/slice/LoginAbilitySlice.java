package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.database.User;
import com.example.myapplication.database.UserInfo;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
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
    private String emailString;
    private String passwordString;
    private Button login;
    private Text error;
    OrmPredicates query;
    DatabaseHelper helper;
    OrmContext context;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        helper = new DatabaseHelper(LoginAbilitySlice.this);
        context = helper.getOrmContext("UserInfo", "UserInfo.db", UserInfo.class);

        email = (TextField) findComponentById(ResourceTable.Id_email_textField_login);
        password = (TextField) findComponentById(ResourceTable.Id_password_textField_login);
        login = (Button) findComponentById(ResourceTable.Id_login_button2);
        error = (Text) findComponentById(ResourceTable.Id_error_login);

        String emailIntentString = String.valueOf(intent.getStringParam("myapp5sankuyogeshemailid"));
        String passwordIntentString = String.valueOf(intent.getStringParam("myapp5sankuyogeshpassword"));
        if(!emailIntentString.equals("null") && !passwordIntentString.equals("null")){
            email.setText(emailIntentString);
            password.setText(passwordIntentString);
        }
        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                emailString = email.getText();
                passwordString = password.getText();
                query = context.where(User.class).equalTo("email", emailString);
                List<User> users = context.query(query);
                if(users.size() > 0 && users.get(0).getPassword().equals(passwordString)){
                    error.setVisibility(Component.HIDE);
                    new ToastDialog(getContext())
                            .setText("Login Successful !!")
                            .setOffset(0,20)
                            .show();
                    present(new HomeAbilitySlice(),new Intent());
                }else{
                    new ToastDialog(getContext())
                            .setText("Try Again")
                            .setOffset(0,20)
                            .show();
                    error.setVisibility(Component.VISIBLE);
                }
            }
        });
    }
}
