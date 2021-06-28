package com.example.trainingapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.trainingapp.ResourceTable;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.data.dataability.DataAbilityPredicates;
import com.example.trainingapp.DbUtils;
import ohos.data.resultset.ResultSet;

public class LoginSlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "LoginSlice");

    TextField email, password;
    Text loginError;
    Button loginBtn;
    @Override
    protected void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);

        email = (TextField) findComponentById(ResourceTable.Id_emailLogin);
        password = (TextField) findComponentById(ResourceTable.Id_passwordLogin);
        loginError = (Text) findComponentById(ResourceTable.Id_loginError);
        loginBtn = (Button) findComponentById(ResourceTable.Id_loginButton);

        loginBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

            }
        });
    }

    private void validateUser() {
        loginError.setText(null);
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.equalTo(DbUtils.DB_COLUMN_EMAIL, email.getText());
        predicates.equalTo(DbUtils.DB_COLUMN_PASSWORD, password.getText());

        ResultSet resultSet = DbUtils.query(this, predicates);
        if(resultSet == null || resultSet.getRowCount()==0) {
            HiLog.error(LABEL_LOG, "resultset empty || getrowcount = 0");
            loginError.setVisibility(Component.VISIBLE);
            return;
        }

        resultSet.goToFirstRow();
        int id = resultSet.getInt(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_PERSON_ID));
        String name = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_FIRSTNAME));
        String email = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_EMAIL));
        String gender = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_GENDER));
        HiLog.info(LABEL_LOG, "query: Id :" + id + " Name :" + name + " Gender :" + gender + " Email :" + email);

        present(new LoginSuccessSlice(), new Intent());

    }
}
