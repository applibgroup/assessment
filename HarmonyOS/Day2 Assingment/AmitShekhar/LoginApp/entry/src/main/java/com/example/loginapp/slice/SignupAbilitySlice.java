package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.multimodalinput.event.KeyEvent;

public class SignupAbilitySlice extends AbilitySlice {
    TextField fn;
    Text fn_err;
    private interface Lambda{
        boolean checkTextField(String s);
    }
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
        setObserver(ResourceTable.Id_first_name, ResourceTable.Id_first_name_error, this::correctName);
        setObserver(ResourceTable.Id_last_name, ResourceTable.Id_last_name_error, this::correctName);
        setObserver(ResourceTable.Id_email, ResourceTable.Id_email_error, this::correctEmail);
        setObserver(ResourceTable.Id_password, ResourceTable.Id_password_error, this::correctPassword);
        setObserver(ResourceTable.Id_phone, ResourceTable.Id_phone_error, this::correctPhone);
    }

    private boolean correctName(String str){
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(!(curr>='A' && curr<='Z')&&!(curr>='a' && curr<='z')&&(curr!=' ')&&(curr!='.')){
                return false;
            }
        }
        return true;
    }

    private boolean correctEmail(String str){
        int dotafterat = 0;
        int at = 0;
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(curr==' '){
                return false;
            }
            if(curr=='@'){
                at += 1;
                dotafterat = 0;
            }
            if(curr=='.'){
                if(at>0) dotafterat += 1;
            }
        }
        if(!(at==1 && dotafterat==1))
            return false;
        return true;
    }

    private boolean correctPassword(String str){
        if(str.length()>=6)
            return true;
        return false;
    }

    private boolean correctPhone(String str){
        if(str.length()!=10)
            return false;
        for(int i=0;i<str.length();i++){
            char curr = str.charAt(i);
            if(!(curr<='9'&&curr>='0'))
                return false;
        }
        return true;
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
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
