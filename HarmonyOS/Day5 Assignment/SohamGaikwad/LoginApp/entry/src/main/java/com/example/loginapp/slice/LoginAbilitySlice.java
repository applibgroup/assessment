package com.example.loginapp.slice;

import com.example.loginapp.DbUtils;
import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class LoginAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "LoginAbilitySlice");

    TextField email, password;
    Text loginError;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        String email_intent = intent.getStringParam("email");
        String password_intent = intent.getStringParam("password");

        email = (TextField) findComponentById(ResourceTable.Id_login_email);
        if (email_intent != null) {
            email.setText(email_intent);
        }
        password = (TextField) findComponentById(ResourceTable.Id_login_password);
        if (password_intent != null) {
            password.setText(password_intent);
        }

        loginError = (Text) findComponentById(ResourceTable.Id_login_error);

        Button btn = (Button) findComponentById(ResourceTable.Id_login_btn);

        btn.setClickedListener(listener -> validateUser());

    }

    private void validateUser() {
        loginError.setText(null);
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.equalTo(DbUtils.DB_COLUMN_EMAIL, email.getText());
        predicates.equalTo(DbUtils.DB_COLUMN_PASSWORD, password.getText());

        ResultSet resultSet = DbUtils.query(this, predicates);
        if(resultSet == null || resultSet.getRowCount()==0) {
            HiLog.error(LABEL_LOG, "resultset empty || getrowcount = 0");
            loginError.setText("Invalid username/password");
            new ToastDialog(getContext())
                    .setText("Invalid username/password")
                    .show();
            return;
        }

        resultSet.goToFirstRow();
        int id = resultSet.getInt(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_PERSON_ID));
        String name = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_FIRSTNAME));
        String email = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_EMAIL));
        String gender = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_GENDER));
        HiLog.info(LABEL_LOG, "query: Id :" + id + " Name :" + name + " Gender :" + gender + " Email :" + email);

        new ToastDialog(getContext())
                .setText("Login Successful")
                .show();

        present(new LoginSuccessAbilitySlice(), new Intent());

    }

}
