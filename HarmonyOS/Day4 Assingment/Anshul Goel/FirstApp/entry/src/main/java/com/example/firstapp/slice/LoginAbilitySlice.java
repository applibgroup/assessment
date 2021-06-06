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

        helper= new DatabaseHelper(LoginAbilitySlice.this);
        context= helper.getOrmContext(getString(ResourceTable.String_UserData),getString(ResourceTable.String_UserData_db), UserData.class);


        Button finalBtn= (Button) findComponentById(ResourceTable.Id_final_login_btn);
        finalBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                OrmPredicates predicates= context.where(User.class).equalTo(getString(ResourceTable.String_email),email_inp.getText());
                List<User> users= context.query(predicates);
                boolean flag = false;
                if(users.size()>0) {
                    if (users.get(0).getPassword().compareTo(pass_inp.getText()) == 0) {
                        flag = true;
                    }
                }

                if (!flag) {
                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_Invalid_Cred))
                            .show();
                }
                else{
                    present(new HomePageAbilitySlice(),new Intent());
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