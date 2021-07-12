package com.example.loginapp.slice;

import com.example.loginapp.ResourceTable;
import com.example.loginapp.StoreDb;
import com.example.loginapp.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {
    private OrmContext ormContext;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        TextField email = (TextField) findComponentById(ResourceTable.Id_email_login);
        TextField password = (TextField) findComponentById(ResourceTable.Id_password_login);
        Button login_btn = (Button) findComponentById(ResourceTable.Id_login_btn);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ormContext = dbHelper.getOrmContext("StoreDb","storeDb.db", StoreDb.class);
        login_btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String email_str = email.getText();
                String password_str = password.getText();
                OrmPredicates predicates = new OrmPredicates(User.class);
                predicates.equalTo("email", email_str);
                List<User> users = ormContext.query(predicates);
                Text signin_complete = (Text) findComponentById(ResourceTable.Id_signin_complete);
                for(int i=0;i<users.size();i++){
                    User curr = users.get(i);
                    if(password_str.equals(curr.getPassword())){
                        signin_complete.setVisibility(Component.VISIBLE);
                        signin_complete.setText("Log in successful!");
                        return;
                    }
                }
                signin_complete.setVisibility(Component.VISIBLE);
                signin_complete.setText("Log in failed!");
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
