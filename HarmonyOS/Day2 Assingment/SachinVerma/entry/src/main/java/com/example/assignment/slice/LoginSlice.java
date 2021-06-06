package com.example.assignment.slice;

import com.example.assignment.DbUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.example.assignment.ResourceTable;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.utils.Color;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);
        final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "LoginSlice");

        Button b = (Button) findComponentById(ResourceTable.Id_button_login);
        b.setClickedListener(component -> {
            String credentials_username;
            String credentials_password;

            TextField username = (TextField) findComponentById(ResourceTable.Id_email_textField_login);
            TextField password = (TextField) findComponentById(ResourceTable.Id_password_textField_login);
            Text enquiry = (Text) findComponentById(ResourceTable.Id_enquiry_login);

            credentials_username = username.getText();
            credentials_password = password.getText();

            HiLog.debug(LABEL_LOG,"username : " + credentials_username);;
            HiLog.debug(LABEL_LOG,"password : " + credentials_password);

            ResultSet result = DbUtils.query(LoginSlice.this, credentials_username);
            if(result == null) {
                HiLog.debug(LABEL_LOG,"USERNAME NOT FOUND");
                enquiry.setTextColor(Color.RED);
            }

            else {
                result.goToFirstRow();
                String userid = result.getString(result.getColumnIndexForName("username"));
                HiLog.debug(LABEL_LOG,"queried username : " + userid);

                String pass = result.getString(result.getColumnIndexForName("password"));
                HiLog.debug(LABEL_LOG,"queried password : " + password);

                if(!credentials_password.equals(pass)) {
                    HiLog.debug(LABEL_LOG,"INCORRECT PASSWORD");
                    enquiry.setTextColor(Color.RED);
                }

                else {
                    HiLog.debug(LABEL_LOG,"CORRECT CREDENTIALS");
                    present(new LoginSuccessSlice(), new Intent());
                    terminate();
                }
            }

            terminate();
        });
    }
}
