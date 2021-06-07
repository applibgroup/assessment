package com.example.myapplication.slice;

import Model.User;
import Utils.DbHelper;
import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.data.rdb.RdbPredicates;
import ohos.data.rdb.RdbStore;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

public class LoginAbilitySlice extends AbilitySlice
{
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    private RdbStore db;
    private String TABLENAME = "test";
    private Text login;

    List<User> queryResult;
    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        login = (Text) findComponentById(ResourceTable.Id_login_ability_text);

        login.setClickedListener(new Component.ClickedListener()
        {
            @Override
            public void onClick(Component component)
            {
                queryData();
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
                    HiLog.warn(LABEL, "USER = " + String.valueOf(user.getFirstName()));
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

        HiLog.warn(LABEL, "Hi");
    }

    @Override
    public void onForeground(Intent intent)
    {
        super.onForeground(intent);
    }
}
