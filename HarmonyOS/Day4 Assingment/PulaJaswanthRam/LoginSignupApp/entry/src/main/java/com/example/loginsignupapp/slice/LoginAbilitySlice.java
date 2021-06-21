package com.example.loginsignupapp.slice;

import com.example.loginsignupapp.ResourceTable;
import com.example.loginsignupapp.data.Db;
import com.example.loginsignupapp.data.User;
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
    private Text emailErr;
    private TextField password;
    private OrmContext ormContext;
    private Button loginBtn;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);

        email = (TextField) findComponentById(ResourceTable.Id_email);
        emailErr = (Text) findComponentById(ResourceTable.Id_email_error);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        loginBtn = (Button) findComponentById(ResourceTable.Id_login_btn);

        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (Email_check(email.getText())){
                emailErr.setVisibility(Component.HIDE);
            }
            else{
                emailErr.setVisibility(Component.VISIBLE);
            }
        };
        email.addTextObserver(observer);

        loginBtn.setClickedListener(this::helper);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ormContext = databaseHelper.getOrmContext("Db", "Db.db", Db.class);
    }

    private void helper(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        String email_txt = email.getText();
        String password_txt = password.getText();

        if (email_txt != null || password_txt != null) {
            OrmPredicates ormPredicates = ormContext.where(User.class);
            ormPredicates.equalTo("email", email_txt);
            ormPredicates.and();
            ormPredicates.equalTo("password", password_txt);
            List<User> list = ormContext.query(ormPredicates);
            if (list != null || !list.isEmpty()) {
                toastDialog.setText("Login Successful").show();
                Intent intent = new Intent();
                intent.setParam("fName", list.get(0).getfName());
                present(new WelcomeSlice(), intent);
            }
            else{
                toastDialog.setText("Invalid credentials").show();
            }
        }
    }

    private boolean Email_check(String str) {
        String x = "([a-zA-Z0-9])*";
        String y = "([a-zA-Z])*";
        return str.matches(x + "[@]" + x + "[.]" + y);
    }
}
