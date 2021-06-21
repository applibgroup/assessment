package com.example.loginsignupapp.data;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class}, version = 1)
public abstract class Db extends OrmDatabase{
}
