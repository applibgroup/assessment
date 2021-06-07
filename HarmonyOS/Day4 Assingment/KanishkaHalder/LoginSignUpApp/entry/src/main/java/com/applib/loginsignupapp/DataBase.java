package com.applib.loginsignupapp;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;
import ohos.data.rdb.RdbOpenCallback;

@Database(entities = {UserModel.class}, version = 1)
public abstract class DataBase extends OrmDatabase {

}
