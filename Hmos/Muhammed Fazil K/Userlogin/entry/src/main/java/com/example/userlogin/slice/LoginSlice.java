package com.example.userlogin.slice;

import com.example.userlogin.ResourceTable;
import com.example.userlogin.Utils.Constants;
import com.example.userlogin.data.User;
import com.example.userlogin.data.UserDatabase;
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

public class LoginSlice extends AbilitySlice {

    OrmContext ormContext;

    TextField loginEmail,loginPassword;
    Text loginEmailError,loginPasswordError;
    Button loginBtn;

    Boolean loginError=true;


    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login_page);
        initViews(intent);
        setListeners();
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        ormContext=databaseHelper.getOrmContext(Constants.DB_ALIAS,Constants.DB_NAME, UserDatabase.class);



    }

    private void initViews(Intent intent) {
        loginEmail=(TextField)findComponentById(ResourceTable.Id_login_email);
        loginPassword=(TextField)findComponentById(ResourceTable.Id_login_password);

        if(intent!=null){
            loginEmail.setText(intent.getStringParam(Constants.EMAIL_COLUMN));
            loginPassword.setText(intent.getStringParam(Constants.PASSWORD_COLUMN));
        }

        loginEmailError=(Text) findComponentById(ResourceTable.Id_loginerror_email);
        loginEmailError.setVisibility(Component.INVISIBLE);
        loginPasswordError=(Text) findComponentById(ResourceTable.Id_loginerror_password);
        loginPasswordError.setVisibility(Component.INVISIBLE);

        loginBtn=(Button)findComponentById(ResourceTable.Id_final_loginbtn);
    }

    private void setListeners() {

        loginEmail.addTextObserver((s, i, i1, i2) -> checkError(s,loginEmailError));

        loginPassword.addTextObserver((s, i, i1, i2) -> {
            if(s.length()<5){
                loginError=true;
                loginPasswordError.setText("Enter a strong password");
                loginPasswordError.setVisibility(Component.VISIBLE);
            }
            else{
                loginError=false;
                loginPasswordError.setVisibility(Component.INVISIBLE);
            }
        });

        loginBtn.setClickedListener(component -> loginUser());
    }

    private void loginUser() {
        if(!loginError){
            OrmPredicates ormPredicates=ormContext.where(User.class);
            ormPredicates.equalTo(Constants.EMAIL_COLUMN,loginEmail.getText());
            ormPredicates.and();
            ormPredicates.equalTo(Constants.PASSWORD_COLUMN,loginPassword.getText());

            List<User> users=ormContext.query(ormPredicates);
            if (users == null || users.isEmpty()) {
                new ToastDialog(this).setText("User no account").setDuration(2000).show();
            } else {
                new ToastDialog(this).setText("User Loggedt").setDuration(2000).show();
                Intent intent=new Intent();
                intent.setParam(Constants.FIRSTNAME_COLUMN,users.get(0).getFirstName());
                present(new WelcomeSlice(), intent);
            }
        }
    }

    private void checkError(String s, Text errEmail) {
        boolean matched = s.matches(Constants.EMAILPATTERN);
        if (s.isEmpty()) {
            errEmail.setText("It is Empty");
            errEmail.setVisibility(Component.VISIBLE);
            loginError = true;
        } else if (matched) {
            errEmail.setVisibility(Component.INVISIBLE);
            loginError = false;
        } else {
            errEmail.setText("Enter Valid data");
            errEmail.setVisibility(Component.VISIBLE);
            loginError = true;
        }
    }
}
