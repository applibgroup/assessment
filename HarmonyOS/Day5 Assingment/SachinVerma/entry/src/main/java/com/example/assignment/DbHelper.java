package com.example.assignment;

import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;

public class DbHelper extends DatabaseHelper {
    public DbHelper(Context context) {
        super(context);
    }

    private static  final RdbOpenCallback callback = new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore rdbStore) {
            rdbStore.executeSql("CREATE TABLE IF NOT EXISTS users(username TEXT PRIMARY KEY, password TEXT NOT NULL, name TEXT NOT NULL, phone TEXT NOT NULL, gender TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(RdbStore rdbStore, int i, int i1) {

        }
    };

    public RdbStore initRdb() {
        StoreConfig config = StoreConfig.newDefaultConfig("database.db");
        return getRdbStore(config, 1, callback, null);
    }
}