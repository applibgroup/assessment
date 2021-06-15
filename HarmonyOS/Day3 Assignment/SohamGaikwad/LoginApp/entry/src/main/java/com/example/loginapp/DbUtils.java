package com.example.loginapp;

import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.app.Context;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

public class DbUtils {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "DbUtils");

    public static final String BASE_URI = "dataability:///com.example.loginapp.PersonDataAbility";

    public static final String DATA_PATH = "/person";

    public static final String DB_COLUMN_PERSON_ID = "id";

    public static final String DB_COLUMN_FIRSTNAME = "first_name";

    public static final String DB_COLUMN_LASTNAME = "last_name";

    public static final String DB_COLUMN_GENDER = "gender";

    public static final String DB_COLUMN_EMAIL = "email";

    public static final String DB_COLUMN_PASSWORD = "password";

    public static final String DB_COLUMN_PHONE = "phone";

    public static ResultSet query(Context context, DataAbilityPredicates predicates) {
        DataAbilityHelper databaseHelper = DataAbilityHelper.creator(context);

        String[] columns = new String[] {DB_COLUMN_PERSON_ID,
                DB_COLUMN_FIRSTNAME, DB_COLUMN_LASTNAME, DB_COLUMN_GENDER, DB_COLUMN_EMAIL, DB_COLUMN_PHONE, DB_COLUMN_PASSWORD};

        ResultSet resultSet = null;

        try {
            resultSet = databaseHelper.query(Uri.parse(BASE_URI + DATA_PATH),
                    columns, predicates);
        } catch (IllegalStateException | DataAbilityRemoteException exception) {
            HiLog.error(LABEL_LOG, "query: dataRemote exception | illegalStateException");
        }
        return resultSet;
    }

    public static void showDB(Context context) {
        ResultSet resultSet = DbUtils.query(context, null);
        if(resultSet == null) {
            HiLog.error(LABEL_LOG, "resultset empty");
            return;
        }
        if(resultSet.getRowCount()==0) {
            HiLog.error(LABEL_LOG, "row count 0");
            return;
        }

        resultSet.goToFirstRow();
        do {
            int id = resultSet.getInt(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_PERSON_ID));
            String name = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_FIRSTNAME));
            String email = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_EMAIL));
            String gender = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_GENDER));
            String password = resultSet.getString(resultSet.getColumnIndexForName(DbUtils.DB_COLUMN_PASSWORD));
            HiLog.info(LABEL_LOG, "query: Id :" + id + " Name :" + name + " Gender :" + gender + " Email :" + email + " Password :" + password);
        } while (resultSet.goToNextRow());
    }

    public static void insert(Context context, String firstname, String lastname, String gender, String email, String phone, String password) {
        DataAbilityHelper databaseHelper = DataAbilityHelper.creator(context);

        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putString(DB_COLUMN_FIRSTNAME, firstname);
        valuesBucket.putString(DB_COLUMN_LASTNAME, lastname);
        valuesBucket.putString(DB_COLUMN_GENDER, gender);
        valuesBucket.putString(DB_COLUMN_EMAIL, email);
        valuesBucket.putString(DB_COLUMN_PHONE, phone);
        valuesBucket.putString(DB_COLUMN_PASSWORD, password);
        try {
            if (databaseHelper.insert(Uri.parse(BASE_URI + DATA_PATH), valuesBucket) != -1) {
                HiLog.info(LABEL_LOG, "insert successful");
            }
        } catch (DataAbilityRemoteException | IllegalStateException exception) {
            HiLog.error(LABEL_LOG, "insert: dataRemote exception|illegalStateException");
        }
    }
}