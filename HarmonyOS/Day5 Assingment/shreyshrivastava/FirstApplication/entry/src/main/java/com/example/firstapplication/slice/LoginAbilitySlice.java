package com.example.firstapplication.slice;

import com.example.firstapplication.ResourceTable;
import com.example.firstapplication.SignUpInfo;
import com.example.firstapplication.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {

    TextField email;
    TextField password;
    Button buttonLogin;

    OrmContext context;
    DatabaseHelper helper;

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG"); //MY_MODULE=

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        helper = new DatabaseHelper(LoginAbilitySlice.this);
        context = helper.getOrmContext("SignUpInfo", "SignUpInfo.db", SignUpInfo.class);

        email = (TextField)findComponentById(ResourceTable.Id_text_login_email);
        password = (TextField)findComponentById(ResourceTable.Id_text_login_password);

        if (intent.hasParameter("email")) {
            email.setText(intent.getStringParam("email"));
            password.setText(intent.getStringParam("password"));
        }

        buttonLogin = (Button)findComponentById(ResourceTable.Id_button_login);
        buttonLogin.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                OrmPredicates queryEmail = context.where(User.class).equalTo("email", email.getText().toString());
                List<User> usersEmail = context.query(queryEmail);
                if (usersEmail.size() > 0) {
                    User user = usersEmail.get(0);
                    String userPassword = user.getPassword();
                    // if (userPassword.compareTo(password.getText()) == 0) {
                    present(new LoginSuccessfulAbilitySlice(), new Intent());
                    //}
                } else {
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Incorrect user information.");
                    toastDialog.setDuration(10000);
                    toastDialog.show();
                }
            }
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
}
