package com.example.myfirstapplication.rdb;

import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;

public class RdbHelper extends DatabaseHelper {

    private static final String TAG = RdbHelper.class.getSimpleName();

    public RdbHelper(Context context) {
        super(context);
    }

    private static RdbOpenCallback callback = new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore store) {
            store.executeSql("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER, salary REAL, blobType BLOB)");
        }
        @Override
        public void onUpgrade(RdbStore store, int oldVersion, int newVersion) {
        }
    };

    public RdbStore initRdb() {
        StoreConfig config = StoreConfig.newDefaultConfig("storeTest.db");
        return getRdbStore(config, 1, callback, null);
    }
}
