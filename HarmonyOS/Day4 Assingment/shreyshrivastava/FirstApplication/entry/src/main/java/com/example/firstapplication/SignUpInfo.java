package com.example.firstapplication;

import com.example.firstapplication.User;
import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class}, version = 1)
public abstract class SignUpInfo extends OrmDatabase {
}
