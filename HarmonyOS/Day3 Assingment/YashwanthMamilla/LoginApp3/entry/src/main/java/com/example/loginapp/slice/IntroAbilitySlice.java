package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import com.example.loginapp.Utils.Constants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class IntroAbilitySlice extends AbilitySlice {


    Text introText;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_intro);
        String firstName = intent.getStringParam(Constants.FIELD_FIRSTNAME);
        String gender = intent.getStringParam(Constants.FIELD_GENDER);
        introText = (Text) findComponentById(ResourceTable.Id_intro_text);

        if (gender == ("M")) {
            introText.setText(this.getString(ResourceTable.String_welcome)+" "+"Mr."+firstName);
        } else {
            introText.setText(this.getString(ResourceTable.String_welcome)+" "+"Ms."+firstName);
        }
    }
}
