package com.android.clark.superappdemo.download.downloadone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskSQLiteHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table task( "
            +"id integer primary key autoincrement,"
            +"url text,"
            +"name text,"
            +"progress integer)";

    public TaskSQLiteHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
