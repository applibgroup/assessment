package com.example.assignment_app;

import ohos.data.orm.OrmDatabase;;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class}, version = 1)
public abstract class Data extends OrmDatabase {
}