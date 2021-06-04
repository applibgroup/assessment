package com.applib.loginsignupapp.slice;

import com.applib.loginsignupapp.DataAbility;
import com.applib.loginsignupapp.DataBase;
import com.applib.loginsignupapp.ResourceTable;
import com.applib.loginsignupapp.UserModel;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.orm.OrmContext;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.util.Arrays;


public class LoginSlice extends AbilitySlice {

    private static final Uri URI =  Uri.parse("dataability:///com.applib.loginsignupapp.DataAbility/test");
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0xD001100, "LOGIN_LOG");

    private TextField username;
    private TextField password;
    private Button submitBtn;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        username = (TextField) findComponentById(ResourceTable.Id_textField_email_login_slice);
        password = (TextField) findComponentById(ResourceTable.Id_textField_password_login_slice);

        submitBtn = (Button) findComponentById(ResourceTable.Id_button_login_login_slice);

        DataAbilityHelper helper = DataAbilityHelper.creator(this,URI);

        ValuesBucket value = new ValuesBucket();
        value.putString("firstName","kanishka");
        value.putString("lastName","halder");
        value.putString("email","k@gmail.com");
        value.putString("phone","9999999999");
        value.putString("password","12345");
        value.putString("gender","Male");
        int result = 0;
        try {
            result = helper.insert(URI, value);
            HiLog.debug(LABEL_LOG, "LOG_RESULT: " + result);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }


        submitBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataAbilityPredicates predicates = new DataAbilityPredicates();
                predicates.equalTo("emailID",username.getText().trim());

                try {
                    ResultSet resultSet = (ResultSet) helper.query(URI, new String[]{"password"},predicates);
                    HiLog.debug(LABEL_LOG, "LOG_RESULT: " + Arrays.toString(resultSet.getAllColumnNames()));
                    resultSet.goToFirstRow();

                    do {
                        int index = resultSet.getRowIndex();
                        String passwordStr = resultSet.getString(index);
                        if(password.getText().trim().equals(passwordStr)){
                            HiLog.debug(LABEL_LOG, "EQUAL CONDOTION: " + passwordStr + " : " + password.getText().trim());
                            present(new HomeSlice(),new Intent());
                            break;
                        }
                    } while(resultSet.goToNextRow());
                } catch (DataAbilityRemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
