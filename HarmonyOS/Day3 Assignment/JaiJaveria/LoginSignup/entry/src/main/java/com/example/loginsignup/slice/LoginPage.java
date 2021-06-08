package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;


public class LoginPage extends PagesSlice {


    private void setUI(int x)
    {
        super.setUIContent(x);

    }
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUI(ResourceTable.Layout_login_page);
        Button button=(Button) findComponentById(ResourceTable.Id_xLoginSubmit);
        setObserver(ResourceTable.Id_xEmailIdL, ResourceTable.Id_xEmailIdErrorL, this::correctEmail);
        setObserver(ResourceTable.Id_xPasswordL, ResourceTable.Id_xPasswordErrorL, this::correctPassword);

        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String inputEmail=((TextField)findComponentById(ResourceTable.Id_xEmailIdL)).getText();
                String inputPass=((TextField)findComponentById(ResourceTable.Id_xPasswordL)).getText();

                if(MainAbilitySlice.checkCredentials(inputEmail, inputPass))
                    setUI(ResourceTable.Layout_login_successful);
                else
                    showToast("Email or Password not in records");

            }
        });
    }

}
