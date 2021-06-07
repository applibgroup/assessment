package com.example.myapplication.slice;

import Utils.CheckInputValidity;
import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class SignupAbilitySlice extends AbilitySlice
{
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        CheckInputValidity validity = new CheckInputValidity();


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


        signUp.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component)
            {

                if(validity.checkFirstName(firstName.getText().trim()) && validity.checkLastName(lastName.getText().trim())
                        && validity.checkEmail(email.getText().trim()) && validity.checkPassword(password.getText()) &&
                        validity.checkMobile(mobile.getText().trim()))
                {
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
    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);
    }
}
