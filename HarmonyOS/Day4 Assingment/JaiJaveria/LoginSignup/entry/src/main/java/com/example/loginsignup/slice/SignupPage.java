package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import com.example.loginsignup.User;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class SignupPage extends PagesSlice {


    private static final String DB_COLN_FirstName="FirstName";
    private static final String DB_COLN_LastName="LastName";
    private static final String DB_COLN_Email="Email";
    private static final String DB_COLN_Password="Password";
    private static final String DB_COLN_MobileNum="MobileNum";
    private static final String DB_COLN_Gender="Gender";
    private static final HiLogLabel LABEL_LOG_I =new HiLogLabel(HiLog.LOG_APP, 0x00201, "SignupPage");
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup_page);
        setObserver(ResourceTable.Id_xFirstName, ResourceTable.Id_xFirstNameError, this::correctName);
        setObserver(ResourceTable.Id_xLastName, ResourceTable.Id_xLastNameError, this::correctName);
        setObserver(ResourceTable.Id_xEmailId, ResourceTable.Id_xEmailIdError, this::correctEmail);
        setObserver(ResourceTable.Id_xPassword, ResourceTable.Id_xPasswordError, this::correctPassword);
        setObserver(ResourceTable.Id_xMobileNo, ResourceTable.Id_xMobileError, this::correctNumber);
        //set observers to update gender on radio buttons
        int radioBs[]={ResourceTable.Id_Male,ResourceTable.Id_Female,ResourceTable.Id_NonBinary, ResourceTable.Id_Others};
        for(int i:radioBs)
        {
            setObserver(i);
        }
        Button signup_page=(Button) findComponentById(ResourceTable.Id_xSignupSubmit);
        signup_page.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String inputEmail=((TextField)findComponentById(ResourceTable.Id_xEmailId)).getText();
                if (MainAbilitySlice.alreadyRegistered(inputEmail))
                {
                    showToast("The Email is already registered. Try a different one.");
                    ((TextField)findComponentById(ResourceTable.Id_xEmailId)).setText("");
                    return;
                }
                User valuesBucket=new User();
                valuesBucket.setFirstName(((TextField)findComponentById(ResourceTable.Id_xFirstName)).getText());
                valuesBucket.setLastName(((TextField)findComponentById(ResourceTable.Id_xLastName)).getText());
                valuesBucket.setEmail(inputEmail);
                valuesBucket.setPassword(((TextField)findComponentById(ResourceTable.Id_xPassword)).getText());
                valuesBucket.setMobileNum(((TextField)findComponentById(ResourceTable.Id_xMobileNo)).getText());
                if (gender.isEmpty())
                {
                    ((Text)findComponentById(ResourceTable.Id_xGenderError)).setVisibility(Component.VISIBLE);
                    HiLog.info(LABEL_LOG_I,"Gender Empty");

                    return;
                }
                else
                {
                    ((Text)findComponentById(ResourceTable.Id_xGenderError)).setVisibility(Component.HIDE);
                }
                valuesBucket.setGender(gender);
                HiLog.info(LABEL_LOG_I,"Calling addUser");

                boolean isSuccess= MainAbilitySlice.addUser(valuesBucket);
                if (isSuccess)
                {
                    showToast("SignUp Successful");
                    clearFields();
                }
                else
                    showToast("SignUp Not Successful");



            }
        });
    }

    private void clearFields() {
        int arr[]={ResourceTable.Id_xFirstName, ResourceTable.Id_xLastName,ResourceTable.Id_xEmailId, ResourceTable.Id_xPassword, ResourceTable.Id_xMobileNo};
        for(int i:arr)
        {
            ((TextField)findComponentById(i)).setText("");
        }

    }


}