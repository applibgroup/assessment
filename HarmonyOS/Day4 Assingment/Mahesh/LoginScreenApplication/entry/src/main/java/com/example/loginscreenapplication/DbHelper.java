package com.example.loginscreenapplication;

import com.example.loginscreenapplication.Model.UserData;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;

public class DbHelper extends DatabaseHelper {

    public static final String TABLE_NAME = "UserDataTable";

    private static final String CREATE_TABLE_SQL =
            String.format("CREATE TABLE IF NOT EXISTS %s(" +
                    "email TEXT PRIMARY KEY, " +
                    "firstName TEXT NOT NULL, " +
                    "lastName TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "mobile TEXT NOT NULL, " +
                    "gender TEXT NOT NULL" +
                    ")", TABLE_NAME);

    public DbHelper(Context context) {
        super(context);
    }

    private static  final RdbOpenCallback callback = new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore rdbStore) {
            rdbStore.executeSql(CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(RdbStore rdbStore, int i, int i1) {

        }
    };

    public RdbStore initRdb(Context context) {
        StoreConfig config = StoreConfig.newDefaultConfig("storeTest.db");
        return getRdbStore(config, 1, callback, null);
    }

    // DB Helper Methods
    public static boolean insertUserData(RdbStore db, UserData userData)
    {

        // Check if entry already exists
        RdbPredicates emailComparisonPredicate = new RdbPredicates(TABLE_NAME).equalTo("email", userData.getEmail());
        ResultSet queryResult = db.query(emailComparisonPredicate, null);
        if (queryResult != null && queryResult.getRowCount() > 0)
        {
            // It means there already exist an entry in the database with the same email id
            return false;
        }

        ValuesBucket valuesBucket = userData.createValuesBucketFromData();

        long rowId = db.insert(TABLE_NAME, valuesBucket);
        return rowId != -1;
    }

    public static UserData getUserDataIfExists(RdbStore db, String email)
    {
        // Check if entry already exists
        RdbPredicates emailComparisonPredicate = new RdbPredicates(TABLE_NAME).equalTo("email", email);
        ResultSet queryResult = db.query(emailComparisonPredicate, null);
        if (queryResult != null && queryResult.getRowCount() > 0)
        {
            queryResult.goToFirstRow();
            // It means there already exist an entry in the database with the same email id
            UserData userData = new UserData();
            userData.setEmail(queryResult.getString(0));
            userData.setFirstName(queryResult.getString(1));
            userData.setLastName(queryResult.getString(2));
            userData.setPassword(queryResult.getString(3));
            userData.setMobile(queryResult.getString(4));

            UserData.Gender gender;
            String queriedGenderString = queryResult.getString(5);
            if (queriedGenderString.equals(UserData.Gender.Male.toString()))
            {
                gender = UserData.Gender.Male;
            }
            else
            {
                gender = UserData.Gender.Female;
            }

            userData.setGender(gender);
            return userData;
        }
        return null;
    }
}
