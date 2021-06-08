package com.example.loginsignup.slice;

import com.example.loginsignup.ResourceTable;
import com.example.loginsignup.StoreDb;
import com.example.loginsignup.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import java.util.List;


public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG_I =new HiLogLabel(HiLog.LOG_APP, 0x00201, "MainAbilitySlice");
    private static DatabaseHelper databaseHelper;
    private static OrmContext ormC;
    private static final String DB_COLN_Email="Email";
    private static final String DB_COLN_Password="Password";
    @Override
    public void onStart(Intent intent) {
        HiLog.info(LABEL_LOG_I,"inside onStart MainAS");
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button login_button = (Button) findComponentById(ResourceTable.Id_xLoginB);
        Button signup_button = (Button) findComponentById(ResourceTable.Id_xSignUpB);
        login_button.setClickedListener(listener ->present(new LoginPage(), new Intent()));
        signup_button.setClickedListener(listener ->present(new SignupPage(), new Intent()));

        HiLog.info(LABEL_LOG_I,"database helper going to be initialized");
        databaseHelper=new DatabaseHelper(this);
        ormC = databaseHelper.getOrmContext("StoreDb", "StoreDb", StoreDb.class);

        HiLog.info(LABEL_LOG_I,"database helper initialized");
        OrmPredicates ormPredicates = new OrmPredicates(User.class);
        query(ormPredicates);
        HiLog.info(LABEL_LOG_I,"exiting onStart MainAS");

    }

    private static void query(OrmPredicates ormPredicates) {
        HiLog.info(LABEL_LOG_I,"Inside Query");

        List<User> list= ormC.query(ormPredicates);
        for(User u: list)
        {
            HiLog.info(LABEL_LOG_I,"db Entry "+u.toString());

        }
    }

    public static boolean addUser(User user)
    {
        HiLog.info(LABEL_LOG_I,"Inside addUser");
        ormC.insert(user);
        boolean isSuccess=ormC.flush();
        HiLog.info(LABEL_LOG_I,"was the insert successful "+isSuccess);
        //debug
        OrmPredicates ormPredicates = new OrmPredicates(User.class);
        query(ormPredicates);

        return isSuccess;

    }
    public static boolean checkCredentials(String Email, String Password)
    {
        OrmPredicates ormPredicates= new OrmPredicates(User.class);
        ormPredicates.equalTo(DB_COLN_Email,Email).equalTo(DB_COLN_Password,Password);
        try {

           List<User> resultSet= ormC.query(ormPredicates);
            if (resultSet == null || resultSet.size() == 0) {
                HiLog.info(LABEL_LOG_I, "query: resultSet is null or no result found");
                return false;
            }
            return resultSet.size()==1;
        } catch (Exception e) {
            HiLog.info(LABEL_LOG_I,"Inside catch block :checkCredentials MainAbility Slice");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean alreadyRegistered(String Email)
    {
        OrmPredicates ormPredicates= new OrmPredicates(User.class);
        ormPredicates.equalTo(DB_COLN_Email,Email);
        try {

            List<User> resultSet= ormC.query(ormPredicates);
            if (resultSet == null) {
                HiLog.info(LABEL_LOG_I, "query: resultSet is null");
                return false;
            }
            return resultSet.size()!=0;
        } catch (Exception e) {
            HiLog.info(LABEL_LOG_I,"Inside catch block :alreadyRegistered MainAbility Slice");
            e.printStackTrace();
        }
        return false;
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
