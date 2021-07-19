package com.applib.loginsignupapp.slice;

import com.applib.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.util.Arrays;


public class LoginSlice extends AbilitySlice {

    private static final Uri URI =  Uri.parse("dataability:///com.applib.loginsignupapp.DataAbility/test");
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0xD001100, "LOGIN_LOG");

    private String EMAIL = "emailID";
    private String PASSWORD = "password";

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

        if(intent.getStringParam(EMAIL) != null){
            String emailStr = intent.getStringParam(EMAIL);
            String passwordStr = intent.getStringParam(PASSWORD);

            username.setText(emailStr);
            password.setText(passwordStr);
        }

        DataAbilityHelper helper = DataAbilityHelper.creator(this,URI);

        submitBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataAbilityPredicates predicates = new DataAbilityPredicates();
                predicates.equalTo("emailID",username.getText());

                try {
                    ResultSet resultSet = (ResultSet) helper.query(URI, new String[]{PASSWORD},predicates);
                    resultSet.goToFirstRow();
                    HiLog.debug(LABEL_LOG,""+ Arrays.toString(resultSet.getAllColumnNames()) + resultSet.getRowCount());

                    boolean loginStatus=false;
                    if(resultSet.getRowCount()>0) {
                        do {
                            int index = resultSet.getRowIndex();
                            String passwordStr = resultSet.getString(index);
                            if (password.getText().trim().equals(passwordStr)) {
                                loginStatus=true;
                                HiLog.debug(LABEL_LOG, "Authentication Successful");
                                new ToastDialog(getContext()).setText("Login Sucessful").setDuration(500).show();
                                Intent intentHome = new Intent();
                                intentHome.setFlags(Intent.FLAG_ABILITY_NEW_MISSION);
                                present(new HomeSlice(), intentHome);
                                break;
                            }
                        } while (resultSet.goToNextRow());
                    }
                    if(!loginStatus)
                        new ToastDialog(getContext()).setText("Invalid Credentials. Try Again!").setDuration(500).show();

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
