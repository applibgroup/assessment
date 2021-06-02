package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

public class SignupPage extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup_page);
        Button button= (Button) findComponentById(ResourceTable.Id_xSignupSubmit);
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                TextField firstName= (TextField) findComponentById(ResourceTable.Id_xFirstName);
                if(!correctName(firstName.getText()))
                {
                    Text text=(Text) findComponentById(ResourceTable.Id_xFirstNameError);
                    text.setVisibility(Component.VISIBLE);
                    return;
                }
                TextField lastName= (TextField) findComponentById(ResourceTable.Id_xFirstName);
                if(!correctName(lastName.getText()))
                {
                    Text text=(Text) findComponentById(ResourceTable.Id_xLastNameError);
                    text.setVisibility(Component.VISIBLE);
                    return;
                }
                TextField email= (TextField) findComponentById(ResourceTable.Id_xEmailId);
                Text text=(Text) findComponentById(ResourceTable.Id_xEmailIdError);
                if(!correctEmail(email.getText(), text))
                {
                    text.setVisibility(Component.VISIBLE);
                    retua@, 
                }

            }
        });

    }

    private boolean correctEmail(String str, Text text)
    {
        String s="([a-zA-Z0-9])*";
        String s2="([a-zA-Z])*";
       return str.matches(s+"[@]"+s+"[\\.]"+s2);
    }

    private boolean correctName(String text) {
        return text.matches("([a-zA-Z])*");
    }
}
