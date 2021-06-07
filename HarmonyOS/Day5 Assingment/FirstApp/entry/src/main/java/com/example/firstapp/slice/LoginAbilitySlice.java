package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import com.example.firstapp.User;
import com.example.firstapp.UserDatabase;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
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
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);
        TextField emailLoginText = (TextField)findComponentById(ResourceTable.Id_EmailLoginText);
        TextField passwordLoginText = (TextField)findComponentById(ResourceTable.Id_PasswordLoginText);
        String emailtext = intent.getStringParam("email");
        String passwordtext = intent.getStringParam("password");
        helper = new DatabaseHelper(LoginAbilitySlice.this);
        emailLoginText.setText(emailtext);
        passwordLoginText.setText(passwordtext);
        context = helper.getOrmContext("UserDatabase", "UserDatabase.db", UserDatabase.class);
        Button loginBtn2 = (Button)findComponentById(ResourceTable.Id_LoginBtn2);
        loginBtn2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                OrmPredicates query = context.where(User.class).equalTo("email", emailLoginText.getText());
                List<User> users = context.query(query);
                String passwordEntry = passwordLoginText.getText();
                int ans=-1;
                for(int i=0;i<users.size();i++)
                {
                    if(passwordEntry.compareTo(users.get(i).getPassword())==0)
                    {
                        ans=i;
                        break;
                    }
                }
                if(ans!=-1)
                {
                    new ToastDialog(getContext())
                            .setText("Login Successfull")
                            .show();
                    Intent intent1 = new Intent();
                    String fullname = users.get(ans).getFirstName() + " " + users.get(ans).getLastName();
                    intent1.setParam("flname", fullname);
                    present(new WelcomePageAbilitySlice(), intent1);
                }
                else
                {
                    new ToastDialog(getContext()).setText("Invalid Credentitals").show();
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
