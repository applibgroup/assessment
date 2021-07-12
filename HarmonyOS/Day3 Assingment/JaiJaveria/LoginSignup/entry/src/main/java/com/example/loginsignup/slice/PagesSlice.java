package com.example.loginsignup.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_PARENT;

public class PagesSlice extends AbilitySlice {
    protected String gender="";

    protected interface Lambda
    {
        boolean checkTextField(String s);
    }

    protected boolean correctPassword(String s) {
        //length of password must be atleast 8
        return s.length()>=8;
    }

    protected boolean correctName(String text) {
        return text.matches("([a-zA-Z])*");
    }

    protected boolean correctEmail(String str) {
        String s = "([a-zA-Z0-9])*";
        String s2 = "([a-zA-Z])*";
        return str.matches(s + "[@]" + s + "[\\.]" + s2);
    }

    protected boolean correctNumber(String str) {
        return str.matches("([0-9])*") && (str.length()==10);
    }
    protected void setObserver(int textFieldID, int errTextID, Lambda l)
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
    protected void setObserver(int radioButtonN ) {
        RadioButton radioButton=(RadioButton)findComponentById(radioButtonN);
        radioButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                gender  = radioButton.getText();
            }
        });
    }
    protected void showToast(String s) {
        new ToastDialog(this).setDuration(1000).setText(s).setSize(MATCH_PARENT,MATCH_CONTENT).show();}



}
