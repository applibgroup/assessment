package com.applib.loginsignupapp.slice;

import com.applib.loginsignupapp.DataAbility;
import com.applib.loginsignupapp.DataBase;
import com.applib.loginsignupapp.ResourceTable;
import com.applib.loginsignupapp.UserModel;
import com.applib.loginsignupapp.component.TextFieldValidated;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.ability.OnClickListener;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.RadioContainer;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.rdb.ValuesBucket;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.util.Arrays;
import java.util.IdentityHashMap;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignUpSlice extends AbilitySlice {

    private static final Uri URI =  Uri.parse("dataability:///com.applib.loginsignupapp.DataAbility/users");
    private static final int[] radioButtonIds = new int[]{ResourceTable.Id_radio_button_male,ResourceTable.Id_radio_button_female};
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0xD001100, "SIGNUP_LOG");

    private String FIRST_NAME;
    private String LAST_NAME;
    private String EMAIL;
    private String PHONE_NUMBER;
    private String PASSWORD;
    private String GENDER;

    private DataAbilityHelper dbHelper;

    private TextFieldValidated firstName;
    private TextFieldValidated lastName;
    private TextFieldValidated emailId;
    private TextFieldValidated phoneNumber;
    private TextFieldValidated password;
    private Button submitBtn;
    private RadioContainer radioContainer;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        FIRST_NAME = this.getString(ResourceTable.String_firstName_field);
        LAST_NAME = this.getString(ResourceTable.String_lastName_field);
        EMAIL = this.getString(ResourceTable.String_email_field);
        PHONE_NUMBER = this.getString(ResourceTable.String_phoneNumber_field);
        PASSWORD = this.getString(ResourceTable.String_password_field);
        GENDER = this.getString(ResourceTable.String_gender_field);

        dbHelper = DataAbilityHelper.creator(this,URI);

        firstName = (TextFieldValidated) findComponentById(ResourceTable.Id_textField_firstName);
        lastName = (TextFieldValidated) findComponentById(ResourceTable.Id_textField_lastName);
        emailId = (TextFieldValidated) findComponentById(ResourceTable.Id_textField_email);
        phoneNumber = (TextFieldValidated) findComponentById(ResourceTable.Id_textField_phone);
        password = (TextFieldValidated) findComponentById(ResourceTable.Id_textField_password);
        submitBtn = (Button) findComponentById(ResourceTable.Id_button_signup_signup_slice);
        radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_radio_container);

        submitBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                boolean isAllValidated = true;
                ValuesBucket value = new ValuesBucket();
                if(firstName.getText()!=null) {
                    value.putString(FIRST_NAME, firstName.getText());
                    isAllValidated = firstName.isValidated() && isAllValidated;
                }
                if(lastName.getText()!=null) {
                    value.putString(LAST_NAME, lastName.getText());
                    isAllValidated = lastName.isValidated() && isAllValidated;
                }
                if(emailId.getText()!=null) {
                    value.putString(EMAIL, emailId.getText());
                    isAllValidated = emailId.isValidated() && isAllValidated;
                }
                if(phoneNumber.getText()!=null) {
                    value.putString(PHONE_NUMBER, phoneNumber.getText());
                    isAllValidated = phoneNumber.isValidated() && isAllValidated;
                }
                if(password.getText()!=null) {
                    value.putString(PASSWORD, password.getText());
                    isAllValidated = password.isValidated() && isAllValidated;
                }


                if(radioContainer.getMarkedButtonId() != -1){
                    int markedId = radioButtonIds[radioContainer.getMarkedButtonId()];
                    RadioButton selectedButton = (RadioButton) findComponentById(markedId);
                    findComponentById(ResourceTable.Id_text_radioGroup_unselected_error).setVisibility(Component.HIDE);
                    value.putString(GENDER, selectedButton.getText());
                } else {
                    findComponentById(ResourceTable.Id_text_radioGroup_unselected_error).setVisibility(Component.VISIBLE);
                    isAllValidated = false;
                }



                if(isAllValidated) {
                    try {
                        int result = dbHelper.insert(URI, value);
                        HiLog.debug(LABEL_LOG, "LOG_RESULT: " + result);
                    } catch (DataAbilityRemoteException e) {
                        e.printStackTrace();
                    }
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
