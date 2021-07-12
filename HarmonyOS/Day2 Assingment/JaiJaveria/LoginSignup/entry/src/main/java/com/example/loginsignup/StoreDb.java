package com.example.loginsignup;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;
import ohos.data.rdb.RdbOpenCallback;

@Database(entities ={User.class}, version=1)
public class StoreDb extends OrmDatabase {
    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public RdbOpenCallback getHelper() {
        return null;
    }
}
