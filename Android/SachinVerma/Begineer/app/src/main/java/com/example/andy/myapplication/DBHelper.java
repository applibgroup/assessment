package com.example.andy.myapplication;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts3.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("Create table contacts(pname String primary key, phone_no String, id String, image_link String)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists contacts");
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public boolean insert_new_contact(String pn, String ppn, String pid, String ppl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pname", pn);
        contentValues.put("phone_no", ppn);
        contentValues.put("id", pid);
        contentValues.put("image_link", ppl);
        long insert = db.insert("contacts", null, contentValues);
        if(insert != -1) return true;
        return false;
    }
}
