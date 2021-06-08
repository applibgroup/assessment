package com.example.firstapplication.slice;

import com.example.firstapplication.ResourceTable;
import com.example.firstapplication.SignUpInfo;
import com.example.firstapplication.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.hiviewdfx.HiLog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {
    TextField firstName;

    TextField lastName;
    Text errorLastName;
    Boolean validLastName;

    TextField email;
    Text errorEmail;
    Boolean validEmail;

    TextField password;
    Text errorPassword;
    Boolean validPassword;

    TextField mobile;
    Text errorMobile;
    Boolean validMobile;

    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    Text errorGender;
    Boolean validGender;

    Button buttonSignUp;

    OrmContext context;
    DatabaseHelper helper;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        helper = new DatabaseHelper(SignupAbilitySlice.this);
        context = helper.getOrmContext("SignUpInfo", "SignUpInfo.db", SignUpInfo.class);

        firstName = (TextField)findComponentById(ResourceTable.Id_text_first_name);

        lastName = (TextField)findComponentById(ResourceTable.Id_text_last_name);
        errorLastName = (Text)findComponentById(ResourceTable.Id_text_error_last_name);
        lastName.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                validLastName = s.chars().allMatch(Character::isLetter);
                if (!validLastName) {
                    errorLastName.setVisibility(0);
                } else {
                    errorLastName.setVisibility(1);
                }
            }
        });

        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        email = (TextField)findComponentById(ResourceTable.Id_text_email);
        errorEmail = (Text)findComponentById(ResourceTable.Id_text_error_email);
        email.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                OrmPredicates queryEmail = context.where(User.class).equalTo("email", s);
                List<User> usersEmail = context.query(queryEmail);

                Matcher matcher = pattern.matcher(s);
                validEmail = matcher.matches() && (usersEmail.size() == 0);
                if (!validEmail) {
                    errorEmail.setVisibility(0);
                } else {
                    errorEmail.setVisibility(1);
                }
            }
        });

        password = (TextField)findComponentById(ResourceTable.Id_text_password);
        errorPassword = (Text)findComponentById(ResourceTable.Id_text_error_password);
        password.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                validPassword = (Boolean) (s.length() >= 5);
                if (!validPassword) {
                    errorPassword.setVisibility(0);
                } else {
                    errorPassword.setVisibility(1);
                }
            }
        });

        mobile = (TextField)findComponentById(ResourceTable.Id_text_mobile);
        errorMobile = (Text)findComponentById(ResourceTable.Id_text_error_mobile);
        mobile.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                validMobile = s.chars().allMatch(Character::isDigit);
                if (!validMobile) {
                    errorMobile.setVisibility(0);
                } else {
                    errorMobile.setVisibility(1);
                }
            }
        });

        radioButtonMale = (RadioButton)findComponentById(ResourceTable.Id_radio_button_male);
        radioButtonFemale = (RadioButton)findComponentById(ResourceTable.Id_radio_button_female);
        errorGender = (Text)findComponentById(ResourceTable.Id_text_error_gender);

        buttonSignUp = (Button)findComponentById(ResourceTable.Id_button_signup);
        buttonSignUp.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String fName = firstName.getText().toString();

                validGender = radioButtonMale.isChecked() ^ radioButtonFemale.isChecked();
                if (!validGender) {
                    errorGender.setVisibility(0);
                } else {
                    errorGender.setVisibility(1);
                }

                ToastDialog toastDialog = new ToastDialog(getContext());

                if (validLastName && validEmail && validPassword && validMobile && validGender) {
                    OrmPredicates predicates= context.where(User.class).equalTo("email", email.getText());
                    List<User> users= context.query(predicates);
                    if(users.size()==0) {
                        User user = new User();
                        user.setFirstName(fName);
                        user.setLastName(lastName.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setMobile(mobile.getText().toString());
                        user.setGender(radioButtonMale.isChecked());
                        boolean isSuccessed = context.insert(user);
                        isSuccessed = context.flush();

                        if (isSuccessed) {
                            toastDialog.setText("Thank you, sign up completed.");
                            showCommonDialog();

                        } else {
                            toastDialog.setText("Error occured.");
                        }
                    } else {
                        toastDialog.setText("Email Id already present.");
                    }
                } else {
                    toastDialog.setText("Please enter valid information.");
                }
                toastDialog.setDuration(10000);
                toastDialog.show();
            }
        });

    }

    private void showCommonDialog() {
        CommonDialog commonDialog= new CommonDialog(this);
        commonDialog.setTitleText("SignUp Success !!");
        commonDialog.setContentText("Go to Login Page to enter the app. Please confirm");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(950,500);
        commonDialog.setButton(IDialog.BUTTON1,"YES",(iDialog, i) -> {
            Intent intent= new Intent();
            intent.setParam("email", email.getText());
            intent.setParam("password", password.getText());
            present(new LoginAbilitySlice(), intent);
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON2,"NO",(iDialog, i) -> {
            iDialog.destroy();
        });
        commonDialog.show();
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
