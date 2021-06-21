package com.devpatel10.loginsignup;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;
import ohos.data.rdb.RdbOpenCallback;

@Database(entities={User.class},version = 1)
public abstract class UserDB extends OrmDatabase {

}
