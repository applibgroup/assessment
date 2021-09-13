package com.assigment.myapp.slice;

import com.assigment.myapp.ResourceTable;
import com.assigment.myapp.Utils.Constants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class IntroSlice extends AbilitySlice {
    private static String GENDER_MALE = "M";
    private static String GENDER_MALE_SALUTATION = "Mr.";
    private static String GENDER_FEMALE_SALUTATION = "Ms.";
    private static String SPACE = " ";

    Text introText;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_intro_page);
        String firstName = intent.getStringParam(Constants.FIELD_FIRSTNAME);
        String gender = intent.getStringParam(Constants.FIELD_GENDER);
        String prefix;
        if (gender.equalsIgnoreCase(GENDER_MALE)) {
            prefix = GENDER_MALE_SALUTATION;
        } else {
            prefix = GENDER_FEMALE_SALUTATION;
        }
        introText = (Text) findComponentById(ResourceTable.Id_intro_text);
        introText.setText(this.getString(ResourceTable.String_welcome)+SPACE+prefix+firstName);

    }
}
