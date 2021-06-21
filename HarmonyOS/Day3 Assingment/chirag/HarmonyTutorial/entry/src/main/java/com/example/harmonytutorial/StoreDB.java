package com.example.harmonytutorial;


import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class StoreDB extends OrmDatabase {
}
