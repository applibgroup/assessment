package com.example.trainingapp.slice;

import ohos.aafwk.ability.AbilitySlice;
import com.example.trainingapp.DbUtils;
import ohos.aafwk.content.Intent;
import com.example.trainingapp.ResourceTable;
import ohos.agp.components.*;
import java.util.regex.Pattern;

import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupSlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "SignupSlice");
    TextField firstName, lastName, email, password, mobile;
    Text firstError, lastError, emailError, passError, mobileError, radioError;
    RadioButton maleRadio,femaleRadio;
    Button signupBtn;
    @Override
    protected void onStart(Intent intent){
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);


        firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        lastName = (TextField) findComponentById((ResourceTable.Id_last_name));
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        mobile = (TextField) findComponentById(ResourceTable.Id_phone);

        maleRadio = (RadioButton) findComponentById(ResourceTable.Id_male);
        femaleRadio = (RadioButton) findComponentById(ResourceTable.Id_female);

        firstError = (Text) findComponentById(ResourceTable.Id_firstError);
        lastError = (Text) findComponentById(ResourceTable.Id_lastError);
        emailError = (Text) findComponentById(ResourceTable.Id_emailError);
        passError = (Text) findComponentById(ResourceTable.Id_passwordError);
        mobileError = (Text) findComponentById(ResourceTable.Id_phoneError);
        radioError = (Text) findComponentById(ResourceTable.Id_radioError);

        signupBtn =(Button) findComponentById(ResourceTable.Id_signup);

        signupBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(checkValidity() == true){
                    String gender;
                    if(maleRadio.isChecked()) gender = "Male";
                    else gender = "Female";
                    DbUtils.insert(SignupSlice.this, firstName.getText(), lastName.getText(), gender, email.getText(), mobile.getText(), password.getText());
                    HiLog.info(LABEL_LOG, "User registered");
                    DbUtils.showDB(SignupSlice.this);

                    CommonDialog commonDialog = new CommonDialog(SignupSlice.this);
                    commonDialog.setTitleText("Common Dialog");
                    commonDialog.setContentText("Login now?");
                    commonDialog.setAutoClosable(true);
                    commonDialog.setSize(1000, MATCH_CONTENT);
                    commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
                        Intent intent1 = new Intent();
                        intent1.setParam("email", email.getText());
                        intent1.setParam("password", password.getText());
                        present(new LoginSlice(), intent1);
                        iDialog.destroy();
                    });
                    commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
                        present(new SignupSuccessSlice(), new Intent());
                        iDialog.destroy();
                    });
                    commonDialog.show();
                }
                else {
                    new ToastDialog(getContext())
                            .setText("Invalid parameters")
                            .show();


                }
            }
        });

    }
    public boolean isNameValid(TextField name){
        String str = name.getText();
        String regex = "[a-zA-Z]+";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isEmailValid(TextField email){
        String str = email.getText();
        String regex = ".+@.+\\..+";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isPhoneValid(TextField mobile){
        String str = mobile.getText();
        String regex = "[1-9][0-9]{9}";
        Pattern p = Pattern.compile(regex);

        return p.matcher(str).matches();
    }

    public boolean isPassValid(TextField password){
        String str = password.getText();
        String regex = ".{5,}";
        Pattern p = Pattern.compile(regex);
        return p.matcher(str).matches();
    }

    public boolean checkValidity(){
        boolean b = true;
        if(firstName.getText() == null || firstName.getText().isEmpty() || !isNameValid(firstName)){
            firstError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            firstError.setVisibility(Component.INVISIBLE);
        }
        if(lastName.getText() == null || lastName.getText().isEmpty() || !isNameValid(lastName)){
            lastError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            lastError.setVisibility(Component.INVISIBLE);
        }
        if(email.getText() == null || email.getText().isEmpty() || !isEmailValid(email)){
            emailError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            emailError.setVisibility(Component.INVISIBLE);
        }
        if(mobile.getText() == null || mobile.getText().isEmpty() || !isPhoneValid(mobile)){
            mobileError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            mobileError.setVisibility(Component.INVISIBLE);
        }
        if(password.getText() == null || password.getText().isEmpty() || !isPassValid(password)){
            passError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            passError.setVisibility(Component.INVISIBLE);
        }
        if(!maleRadio.isChecked() && !femaleRadio.isChecked()){
            radioError.setVisibility(Component.VISIBLE);
            b = false;
        }
        else{
            radioError.setVisibility(Component.INVISIBLE);
        }
        return b;
    }
}
