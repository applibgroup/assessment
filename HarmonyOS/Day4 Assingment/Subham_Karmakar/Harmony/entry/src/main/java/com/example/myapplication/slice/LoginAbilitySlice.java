package com.example.myapplication.slice;

import Model.User;
import Utils.DbHelper;
import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.data.rdb.RdbPredicates;
import ohos.data.rdb.RdbStore;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;

public class LoginAbilitySlice extends AbilitySlice
{
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    private RdbStore db;
    private String TABLENAME = "test";
    private Text login;

    private int flag = 0;

    ArrayList<User> queryResult;

    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        TextField emailLogin = (TextField) findComponentById(ResourceTable.Id_email_login);
        TextField passwordLogin = (TextField) findComponentById(ResourceTable.Id_password_login);
        Text emailError = (Text) findComponentById(ResourceTable.Id_email_login_error);
        Text passwordError = (Text) findComponentById(ResourceTable.Id_password_login_error);
        Button login = (Button) findComponentById(ResourceTable.Id_login_confirmation);
        Text loginState = (Text) findComponentById(ResourceTable.Id_login_state);

        emailLogin.setText("");
        passwordLogin.setText("");
        emailError.setText("");
        passwordError.setText("");
        loginState.setText("");

        login.setClickedListener(new Component.ClickedListener()
        {
            @Override
            public void onClick(Component component)
            {
                flag = 0;
                queryData();
                User match = new User();
                for(User result : queryResult)
                {
                    if(emailLogin.getText().trim().equals(result.getEmail()))
                    {
                        flag = 1;

                        match.setFirstName(result.getFirstName());
                        match.setLastName(result.getLastName());
                        match.setEmail(result.getEmail());
                        match.setPassword(result.getPassword());
                        match.setMobile(result.getMobile());
                        match.setGender(result.getGender());
                        break;
                    }
                }

                if(flag == 0)
                {
                    emailError.setText("No such email found. Please Sign Up if not yet!");
                    passwordError.setText("");
                    loginState.setText("");
                }
                else
                {
                    emailError.setText("");
                    loginState.setText("");
                    passwordError.setText("");
                    if(match.getPassword().equals(passwordLogin.getText()))
                    {
                        loginState.setText("Successfully logged in!");
                    }
                    else
                    {
                        passwordError.setText("Wrong password entered");
                    }

                }
            }
        });


    }

    private void queryData()
    {
        String[] columns = new String[] {"firstname", "lastname", "email", "password", "mobile", "gender"};
        RdbPredicates rdbPredicates = new RdbPredicates(TABLENAME);
        ResultSet resultSet = db.query(rdbPredicates, columns);
        queryResult = new ArrayList<User>();
        if(resultSet!=null)
        {
            if(resultSet.getRowCount() > 0)
            {
                while(resultSet.goToNextRow())
                {
                    User user = new User();
                    user.setFirstName(resultSet.getString(0));
                    user.setLastName(resultSet.getString(1));
                    user.setEmail(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setMobile(resultSet.getString(4));
                    user.setGender(resultSet.getInt(5));
                    queryResult.add(user);
                }
            }
            else
            {
                HiLog.warn(LABEL,"Nothing to show");
            }
        }


    }

    @Override
    public void onActive()
    {
        super.onActive();
        DbHelper dbHelper = new DbHelper(this);
        db = dbHelper.initRdb(this);
    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);
    }
}
