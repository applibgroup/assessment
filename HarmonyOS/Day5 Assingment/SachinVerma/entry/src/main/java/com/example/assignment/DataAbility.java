package com.example.assignment;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

public class DataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "DataAbility");
    RdbStore rdbStore;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "DataAbility onStart");
        DbHelper dbHelper = new DbHelper(this);
        rdbStore = dbHelper.initRdb();
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        RdbPredicates Predicates = DataAbilityUtils.createRdbPredicates(predicates,"users");
        return rdbStore.query(Predicates, columns);
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "DataAbility insert");
        HiLog.debug(LABEL_LOG, "username : " + value.getString("username"));
        HiLog.debug(LABEL_LOG, "password : " + value.getString("password"));
        HiLog.debug(LABEL_LOG, "name : " + value.getString("name"));
        HiLog.debug(LABEL_LOG, "phone : " + value.getString("phone"));
        HiLog.debug(LABEL_LOG, "gender : " + value.getString("gender"));

        long res = rdbStore.insert("users", value);
        return Math.toIntExact(res);
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        //add custom impl.
        return 0;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        //add custom impl.
        return 0;
    }
}