package com.example.loginsignupapp.slice;

import com.example.loginsignupapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

public class SignupAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);
        checker(ResourceTable.Id_lName, ResourceTable.Id_lName_error, this::Name_check);
        checker(ResourceTable.Id_email, ResourceTable.Id_email_error, this::Email_check);
        checker(ResourceTable.Id_password, ResourceTable.Id_password_error, this::Password_check);
        checker(ResourceTable.Id_mobile, ResourceTable.Id_mobile_error, this::Number_check);
    }

    private boolean Name_check(String text) {
        return text.matches("([a-zA-Z])*");
    }

    private boolean Email_check(String str) {
        String x = "([a-zA-Z0-9])*";
        String y = "([a-zA-Z])*";
        return str.matches(x + "[@]" + x + "[.]" + y);
    }

    private boolean Password_check(String s) {
        return s.length()>=5;
    }

    private boolean Number_check(String str) {
        return str.matches("([0-9])*") && (str.length()==10);
    }

    public void checker(int Field_Id, int Err_Id, Lambda l)
    {
        TextField textField = (TextField) findComponentById(Field_Id);
        Text err_text = (Text) findComponentById(Err_Id);

        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (l.function(textField.getText())){
                err_text.setVisibility(Component.HIDE);
            }
            else{
                err_text.setVisibility(Component.VISIBLE);
            }
        };

        textField.addTextObserver(observer);
    }

    private interface Lambda {
        boolean function(String s);
    }
}
