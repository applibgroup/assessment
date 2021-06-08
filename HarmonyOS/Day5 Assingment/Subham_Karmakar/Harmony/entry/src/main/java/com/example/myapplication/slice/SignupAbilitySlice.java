package com.example.myapplication.slice;

import Model.User;
import Utils.CheckInputValidity;
import Utils.DatabaseMethods;
import Utils.DbHelper;
import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.ValuesBucket;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupAbilitySlice extends AbilitySlice
{
    private RdbStore db;

    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    private String TABLENAME = "test";
    public static final float DIALOG_BOX_CORNER_RADIUS = 36.0f;
    public static final int DIALOG_BOX_WIDTH = 984;

    private Text signupState;
    private Button signUp;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField password;
    private TextField mobile;
    private Text firstNameError;
    private Text lastNameError;
    private Text emailError;
    private Text passwordError;
    private Text mobileError;
    private RadioContainer radioContainer;

    private User user;

    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);


        CheckInputValidity validity = new CheckInputValidity();
        DatabaseMethods dbm = new DatabaseMethods();
        user = new User();

        signUp = (Button) findComponentById(ResourceTable.Id_signup_confirmation);
        firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        lastName = (TextField) findComponentById(ResourceTable.Id_last_name);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        firstNameError = (Text) findComponentById(ResourceTable.Id_first_name_error);
        lastNameError = (Text) findComponentById(ResourceTable.Id_last_name_error);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);
        radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_radio_container);
        signupState = (Text) findComponentById(ResourceTable.Id_signup_state);

        firstName.setText("");
        lastName.setText("");
        email.setText("");
        password.setText("");
        mobile.setText("");
        firstNameError.setText("");
        lastNameError.setText("");
        emailError.setText("");
        passwordError.setText("");
        mobileError.setText("");
        signupState.setText("");

        signUp.setClickedListener(new Component.ClickedListener()
        {
            @Override
            public void onClick(Component component)
            {
                signupState.setText("");

                if(validity.checkFirstName(firstName.getText().trim()) && validity.checkLastName(lastName.getText().trim())
                        && validity.checkEmail(email.getText().trim()) && validity.checkPassword(password.getText()) &&
                        validity.checkMobile(mobile.getText().trim()))
                {
                    user.setFirstName(firstName.getText().trim());
                    user.setLastName(lastName.getText().trim());
                    user.setEmail(email.getText().trim());
                    user.setPassword(password.getText());
                    user.setMobile(mobile.getText().trim());
                    user.setGender(radioContainer.getMarkedButtonId());

                    insertData(firstName.getText().trim(),
                            lastName.getText().trim(), email.getText().trim(),
                            password.getText(), mobile.getText().trim(), radioContainer.getMarkedButtonId());


                }
                else
                {
                    if(!validity.checkFirstName(firstName.getText().trim()))
                    {
                        new ToastDialog(getContext()).setText("One or more error!").setDuration(1000).show();
                        firstNameError.setText("Please enter a valid First Name with first letter capital");
                    }
                    else
                    {
                        firstNameError.setText("");
                    }
                    if(!validity.checkLastName(lastName.getText().trim()))
                    {
                        new ToastDialog(getContext()).setText("One or more error!").setDuration(1000).show();
                        lastNameError.setText("Please enter a valid Last Name with first letter capital");
                    }
                    else
                    {
                        lastNameError.setText("");
                    }
                    if(!validity.checkEmail(email.getText().trim()))
                    {
                        new ToastDialog(getContext()).setText("One or more error!").setDuration(1000).show();
                        emailError.setText("Please enter a valid Email ID");
                    }
                    else
                    {
                        emailError.setText("");
                    }
                    if(!validity.checkPassword(password.getText()))
                    {
                        new ToastDialog(getContext()).setText("One or more error!").setDuration(1000).show();
                        passwordError.setText("Please enter a strong password");
                    }
                    else
                    {
                        passwordError.setText("");
                    }
                    if(!validity.checkMobile(mobile.getText().trim()))
                    {
                        new ToastDialog(getContext()).setText("One or more error!").setDuration(1000).show();
                        mobileError.setText("Please enter a valid Mobile number");
                    }
                    else
                    {
                        mobileError.setText("");
                    }
                }
            }
        });

    }

    @Override
    public void onActive()
    {
        super.onActive();
        DbHelper dbHelper = new DbHelper(this);
        db = dbHelper.initRdb(this);
        HiLog.warn(LABEL, "DB init");

    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);
    }

    private void showCommonDialog()
    {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("   Signup Successful");
        commonDialog.setContentText("    Do you want to go to login page?");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT);

        commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) ->
        {
            Intent intent = new Intent();
            intent.setParam("email", user.getEmail());
            intent.setParam("password", user.getPassword());
            present(new LoginAbilitySlice(), intent);
            terminate();
            iDialog.destroy();
        });

        commonDialog.setButton(IDialog.BUTTON2, "No", (iDialog, i) ->
        {
            iDialog.destroy();
        });

        commonDialog.show();
    }

    private void showToast(String s)
    {
        new ToastDialog(getContext()).setText(s).setDuration(1000).show();
    }


    private void insertData(String firstName, String lastName, String email, String password, String mobile, int gender)
    {
        ValuesBucket valuesBucket =  new ValuesBucket();
        valuesBucket.putString("firstname", firstName);
        valuesBucket.putString("lastname", lastName);
        valuesBucket.putString("email", email);
        valuesBucket.putString("password", password);
        valuesBucket.putString("mobile", mobile);
        valuesBucket.putInteger("gender", gender);
        if(db.insert(TABLENAME, valuesBucket) > 0)
        {
            HiLog.warn(LABEL, "Successfully added to database");
            signupState.setText("Account created successfully!");
            showCommonDialog();
        }
        else
        {
            new ToastDialog(getContext()).setText("Error adding to database!").setDuration(1000).show();
            HiLog.warn(LABEL, "There was an error while adding to database");
            signupState.setText("There was some error in creating the account");
        }
    }
}
