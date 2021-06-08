package com.example.basic;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class}, version = 1)
public abstract class StoreDb extends OrmDatabase {
}
