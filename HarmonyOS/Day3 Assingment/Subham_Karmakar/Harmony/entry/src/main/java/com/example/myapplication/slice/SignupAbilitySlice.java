package com.example.myapplication.slice;

import Model.User;
import Utils.CheckInputValidity;
import Utils.DatabaseMethods;
import Utils.DbHelper;
import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.ValuesBucket;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class SignupAbilitySlice extends AbilitySlice
{
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    private RdbStore db;
    private String TABLENAME = "test";

    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);


        CheckInputValidity validity = new CheckInputValidity();
        DatabaseMethods dbm = new DatabaseMethods();
        User user = new User();

        Button signUp = (Button) findComponentById(ResourceTable.Id_signup_confirmation);
        TextField firstName = (TextField) findComponentById(ResourceTable.Id_first_name);
        TextField lastName = (TextField) findComponentById(ResourceTable.Id_last_name);
        TextField email = (TextField) findComponentById(ResourceTable.Id_email);
        TextField password = (TextField) findComponentById(ResourceTable.Id_password);
        TextField mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        Text firstNameError = (Text) findComponentById(ResourceTable.Id_first_name_error);
        Text lastNameError = (Text) findComponentById(ResourceTable.Id_last_name_error);
        Text emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        Text passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        Text mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);
        RadioContainer radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_radio_container);

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

        signUp.setClickedListener(new Component.ClickedListener()
        {
            @Override
            public void onClick(Component component)
            {

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

                    HiLog.warn(LABEL, "All Correct");

                }
                else
                {
                    if(!validity.checkFirstName(firstName.getText().trim()))
                    {
                        firstNameError.setText("Please enter a valid First Name with first letter capital");
                    }
                    else
                    {
                        firstNameError.setText("");
                    }
                    if(!validity.checkLastName(lastName.getText().trim()))
                    {
                        lastNameError.setText("Please enter a valid Last Name with first letter capital");
                    }
                    else
                    {
                        lastNameError.setText("");
                    }
                    if(!validity.checkEmail(email.getText().trim()))
                    {
                        emailError.setText("Please enter a valid Email ID");
                    }
                    else
                    {
                        emailError.setText("");
                    }
                    if(!validity.checkPassword(password.getText()))
                    {
                        passwordError.setText("Please enter a strong password");
                    }
                    else
                    {
                        passwordError.setText("");
                    }
                    if(!validity.checkMobile(mobile.getText().trim()))
                    {
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
        HiLog.warn(LABEL, "DB init " + String.valueOf(db));

    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);
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
        db.insert(TABLENAME, valuesBucket);

    }
}
