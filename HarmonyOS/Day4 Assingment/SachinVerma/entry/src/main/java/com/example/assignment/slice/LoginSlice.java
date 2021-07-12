package com.example.assignment.slice;

import com.example.assignment.DbUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.assignment.ResourceTable;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);
        DirectionalLayout toastLayout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_toast, null, false);

        final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "LoginSlice");

        Button b = (Button) findComponentById(ResourceTable.Id_button_login);
        b.setClickedListener(component -> {
            String credentials_username;
            String credentials_password;
            boolean empty = false;

            TextField username = (TextField) findComponentById(ResourceTable.Id_email_textField_login);
            TextField password = (TextField) findComponentById(ResourceTable.Id_password_textField_login);
//            Text enquiry = (Text) findComponentById(ResourceTable.Id_enquiry_login);

            credentials_username = username.getText();
            credentials_password = password.getText();

            if(credentials_username.equals("") || credentials_password.equals("")) {
                empty = true;
            }

            HiLog.debug(LABEL_LOG,"username : " + credentials_username);;
            HiLog.debug(LABEL_LOG,"password : " + credentials_password);
            HiLog.debug(LABEL_LOG, "empty : " + empty);

            ResultSet result = null;
            if(!empty) {
                result = DbUtils.query(LoginSlice.this, credentials_username);
            }
            if(result == null || empty) {
                HiLog.debug(LABEL_LOG,"USERNAME NOT FOUND");
                new ToastDialog(getContext())
                    .setContentCustomComponent(toastLayout)
                    .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                    .setAlignment(LayoutAlignment.BOTTOM)
                    .show();
                HiLog.debug(LABEL_LOG,"WORKING");
            }

            else {
                result.goToFirstRow();
                String userid = result.getString(result.getColumnIndexForName("username"));
                HiLog.debug(LABEL_LOG,"queried username : " + userid);

                String pass = result.getString(result.getColumnIndexForName("password"));
                HiLog.debug(LABEL_LOG,"queried password : " + password);

                if(!credentials_password.equals(pass)) {
                    HiLog.debug(LABEL_LOG,"INCORRECT PASSWORD");
                    new ToastDialog(getContext())
                        .setContentCustomComponent(toastLayout)
                        .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                        .setAlignment(LayoutAlignment.BOTTOM)
                        .show();
                }

                else {
                    HiLog.debug(LABEL_LOG,"CORRECT CREDENTIALS");
                    new ToastDialog(getContext())
                        .setText("Login Successful")
                        .setAlignment(LayoutAlignment.CENTER)
                        .show();
                    present(new LoginSuccessSlice(), new Intent());
                    username.setText("");
                    password.setText("");
                    terminate();
                }
            }
        });
    }
}
