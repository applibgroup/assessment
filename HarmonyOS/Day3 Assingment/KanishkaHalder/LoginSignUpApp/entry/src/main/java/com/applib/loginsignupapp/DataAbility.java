package com.applib.loginsignupapp;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.FileDescriptor;

public class DataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "DB_LOG");

    private OrmContext ormContext = null;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "DataAbility onStart");
        DatabaseHelper helper = new DatabaseHelper(this);
        ormContext = helper.getOrmContext("test","test.db", DataBase.class);
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {

        // Query data in the database.
        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates,UserModel.class);
        ResultSet resultSet = ormContext.query(ormPredicates, columns);
        if (resultSet == null) {
            HiLog.info(LABEL_LOG, "resultSet is null");
        }

        // Return the query result.
        return resultSet;
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "DataAbility insert");
        UserModel userModel = new UserModel();
        if(value.getString("firstName") != null)
            userModel.setFirstname(value.getString("firstName"));
        if(value.getString("lastName") != null)
            userModel.setLastName(value.getString("lastName"));
        if(value.getString("email") != null)
            userModel.setEmailID(value.getString("email"));
        if(value.getString("phone") != null)
            userModel.setPhoneNumber(value.getString("phone"));
        if(value.getString("password") != null)
            userModel.setPassword(value.getString("password"));
        if(value.getString("gender") != null)
            userModel.setGender(value.getString("gender"));

        boolean isSuccessful = ormContext.insert(userModel);
        if (!isSuccessful) {
            HiLog.error(LABEL_LOG, "failed to insert");
            return -1;
        }
        try {
            isSuccessful = ormContext.flush();
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        if (!isSuccessful) {
            HiLog.error(LABEL_LOG, "failed to insert flush");
            return -1;
        }
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        int id = Math.toIntExact(userModel.getRowId());
        return id;
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to delete, ormContext is null");
            return -1;
        }

        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates,UserModel.class);
        int value = ormContext.delete(ormPredicates);
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        return value;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {

        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to update, ormContext is null");
            return -1;
        }

        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates,UserModel.class);
        int index = ormContext.update(ormPredicates, value);
        HiLog.info(LABEL_LOG, "UserDataAbility update value:" + index);
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        return index;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        return null;
    }

    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}