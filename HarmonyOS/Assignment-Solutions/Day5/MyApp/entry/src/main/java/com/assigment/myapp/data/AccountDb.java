package com.assigment.myapp.data;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class}, version = 1)
public abstract class AccountDb extends OrmDatabase {

}
