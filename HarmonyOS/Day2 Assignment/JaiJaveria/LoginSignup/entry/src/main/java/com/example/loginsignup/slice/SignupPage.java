package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

public class SignupPage extends AbilitySlice {

    private interface Lambda
    {
        boolean checkTextField(String s);
    }
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup_page);
        setObserver(ResourceTable.Id_xFirstName, ResourceTable.Id_xFirstNameError, this::correctName);
        setObserver(ResourceTable.Id_xLastName, ResourceTable.Id_xLastNameError, this::correctName);
        setObserver(ResourceTable.Id_xEmailId, ResourceTable.Id_xEmailIdError, this::correctEmail);
        setObserver(ResourceTable.Id_xPassword, ResourceTable.Id_xPasswordError, this::correctPassword);
        setObserver(ResourceTable.Id_xMobileNo, ResourceTable.Id_xMobileError, this::correctNumber);
    }

    private boolean correctPassword(String s) {
        //length of password must be atleast 8
        return s.length()>=8;
    }

    private boolean correctName(String text) {
        return text.matches("([a-zA-Z])*");
    }

    private boolean correctEmail(String str) {
        String s = "([a-zA-Z0-9])*";
        String s2 = "([a-zA-Z])*";
        return str.matches(s + "[@]" + s + "[\\.]" + s2);
    }

    private boolean correctNumber(String str) {
        return str.matches("([0-9])*") && (str.length()==10);
    }

    public void setObserver(int textFieldID, int errTextID, Lambda l)
    {
        TextField textField = (TextField) findComponentById(textFieldID);
        Text text = (Text) findComponentById(errTextID);
        Text.TextObserver observer = new Text.TextObserver()
        {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2)
            {

                if (!l.checkTextField(textField.getText()))
                {
                    text.setVisibility(Component.VISIBLE);
                    return;
                }
                else {
                    text.setVisibility(Component.HIDE);

                }


            }
        };
        textField.addTextObserver(observer);

    }
}
