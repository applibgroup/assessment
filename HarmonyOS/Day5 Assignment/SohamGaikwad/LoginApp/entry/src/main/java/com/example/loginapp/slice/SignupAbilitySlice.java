package com.example.loginapp.slice;

import com.example.loginapp.DbUtils;
import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.RadioButton;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.regex.Pattern;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "SignupAbilitySlice");
    private static final int DIALOG_BOX_WIDTH = 984;

    boolean check;

    TextField firstName, lastName, email, mobile, password;
    Text firstNameError, lastNameError, emailError, mobileError, passwordError, genderError;
    RadioButton maleRadio, femaleRadio;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        check = true;

        firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        lastName = (TextField) findComponentById(ResourceTable.Id_last_name);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        maleRadio = (RadioButton) findComponentById(ResourceTable.Id_radio_male);
        femaleRadio = (RadioButton) findComponentById(ResourceTable.Id_radio_female);

        firstNameError = (Text) findComponentById(ResourceTable.Id_first_name_error);
        lastNameError = (Text) findComponentById(ResourceTable.Id_last_name_error);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        genderError = (Text) findComponentById(ResourceTable.Id_gender_error);

        Button btn = (Button) findComponentById(ResourceTable.Id_signup_btn);

        btn.setClickedListener(component -> {
            if (checkAll()) {
                String gender = maleRadio.isChecked() ? "Male" : "Female";
                DbUtils.insert(SignupAbilitySlice.this, firstName.getText(), lastName.getText(), gender, email.getText(), mobile.getText(), password.getText());
                HiLog.info(LABEL_LOG, "User registered");
                DbUtils.showDB(SignupAbilitySlice.this);

                CommonDialog commonDialog = new CommonDialog(this);
                commonDialog.setTitleText("Common Dialog");
                commonDialog.setContentText("Login now?");
                commonDialog.setAutoClosable(true);
                commonDialog.setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT);
                commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
                    Intent intent1 = new Intent();
                    intent1.setParam("email", email.getText());
                    intent1.setParam("password", password.getText());
                    present(new LoginAbilitySlice(), intent1);

                    iDialog.destroy();
                });
                commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
                    present(new RegisterSuccessAbilitySlice(), new Intent());
                    iDialog.destroy();
                });
                commonDialog.show();

            }
            else {
                new ToastDialog(getContext())
                        .setText("Invalid parameters")
                        .show();
            }
        });
    }

    public static boolean isEmailValid(String email)
    {

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone)
    {

        String regex = "\\+?\\d[\\d -]{8,12}\\d";

        Pattern pat = Pattern.compile(regex);
        if (phone == null)
            return false;
        return pat.matcher(phone).matches();
    }

    private boolean checkAll() {
        check = true;
        firstNameError.setText(null);
        lastNameError.setText(null);
        emailError.setText(null);
        mobileError.setText(null);
        passwordError.setText(null);
        genderError.setText(null);

        if (firstName.length() == 0) {
            check = false;
            firstNameError.setText("Cannot be empty!");
        }

        if (lastName.length() == 0) {
            check = false;
            lastNameError.setText("Cannot be empty!");
        }

        if (password.length() == 0) {
            check = false;
            passwordError.setText("Cannot be empty!");
        }

        if (!isEmailValid(email.getText())) {
            check = false;
            emailError.setText("Invalid email");
        }

        if (!isPhoneValid(mobile.getText())) {
            check = false;
            mobileError.setText("Invalid phone");
        }

        if ((!maleRadio.isChecked()) && (!femaleRadio.isChecked())) {
            check = false;
            genderError.setText("Select gender required");
        }

        return check;
    }


}
