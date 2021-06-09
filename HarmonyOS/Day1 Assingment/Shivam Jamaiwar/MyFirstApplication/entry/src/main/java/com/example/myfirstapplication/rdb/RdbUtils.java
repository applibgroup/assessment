package com.example.myfirstapplication.rdb;

import ohos.app.Context;
import ohos.data.rdb.RdbPredicates;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class RdbUtils {

    private static final String TAG = RdbUtils.class.getSimpleName();
    RdbStore store;

    public RdbUtils(Context context) {
        store = new RdbHelper(context).initRdb();
    }

    public void insertData() {
        ValuesBucket values = new ValuesBucket();
        values.putInteger("id", 2);
        values.putString("name", "Ritesh");
        values.putInteger("age", 18);
        values.putDouble("salary", 100.5);
        values.putByteArray("blobType", new byte[] {1, 2, 3});
        long id = store.insert("test", values);
        HiLog.error(new HiLogLabel(0, 0, TAG), "Id: " + id);
    }

    public void getData() {
        String[] columns = new String[] {"id", "name", "age", "salary"};
        RdbPredicates rdbPredicates = new RdbPredicates("test").equalTo("age", 18).orderByAsc("salary");
        ResultSet resultSet = store.query(rdbPredicates, columns);
        HiLog.error(new HiLogLabel(0, 0, TAG), "Result Set: " + resultSet.toString());
        boolean result = resultSet.goToNextRow();
        HiLog.error(new HiLogLabel(0, 0, TAG), "Result: " + result);
    }
}
