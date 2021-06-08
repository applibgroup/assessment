package com.applib.loginsignupapp;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {UserModel.class}, version = 2)
public abstract class DataBase extends OrmDatabase {

}
