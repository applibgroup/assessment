package com.example.assignment;

import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.app.Context;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.util.ArrayList;

public class DbUtils {
    private static final Uri uri = Uri.parse("dataability:///com.example.assignment.DataAbility/users");
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "DbUtils");

    public DbUtils() {
    }

    public static void insert(Context context, ArrayList<String> data) {
        HiLog.debug(LABEL_LOG, "username : " + data.get(0));
        HiLog.debug(LABEL_LOG, "password : " + data.get(1));
        HiLog.debug(LABEL_LOG, "name : " + data.get(2));
        HiLog.debug(LABEL_LOG, "phone : " + data.get(3));
        HiLog.debug(LABEL_LOG, "gender : " + data.get(4));

        DataAbilityHelper dataAbilityHelper = DataAbilityHelper.creator(context, uri);
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putString("username", data.get(0));
        valuesBucket.putString("password", data.get(1));
        valuesBucket.putString("name", data.get(2));
        valuesBucket.putString("phone", data.get(3));
        valuesBucket.putString("gender", data.get(4));

        try {
            dataAbilityHelper.insert(uri, valuesBucket);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(Context context, String data) {
        ResultSet resultSet = null;
        DataAbilityHelper dataAbilityHelper = DataAbilityHelper.creator(context, uri);
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.equalTo("username", data);

        try {
            resultSet = dataAbilityHelper.query(uri, null, predicates);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}