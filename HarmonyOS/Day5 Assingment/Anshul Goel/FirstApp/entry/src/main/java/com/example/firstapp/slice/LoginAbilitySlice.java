package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import com.example.firstapp.User;
import com.example.firstapp.UserData;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {
    private DatabaseHelper helper;
    private OrmContext context;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        TextField email_inp= (TextField)findComponentById(ResourceTable.Id_email_inp);
        TextField pass_inp= (TextField) findComponentById(ResourceTable.Id_pass_inp);

        if(intent.hasParameter(getString(ResourceTable.String_Email))) {
            String s1= intent.getStringParam(getString(ResourceTable.String_Email));
            String s2= intent.getStringParam(getString(ResourceTable.String_Password));
            email_inp.setText(s1);
            pass_inp.setText(s2);
        }

        helper= new DatabaseHelper(LoginAbilitySlice.this);
        context= helper.getOrmContext(getString(ResourceTable.String_UserData),getString(ResourceTable.String_UserData_db), UserData.class);


        Button finalBtn= (Button) findComponentById(ResourceTable.Id_final_login_btn);
        finalBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                OrmPredicates predicates = context.where(User.class).equalTo(getString(ResourceTable.String_email),email_inp.getText()).equalTo(getString(ResourceTable.String_pass),pass_inp.getText());
                List<User> users = context.query(predicates);

                if (users.size()==0) {
                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_Invalid_Cred))
                            .show();
                } else {
                    present(new HomePageAbilitySlice(), new Intent());
                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_login_msg))
                            .show();
                }

            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}