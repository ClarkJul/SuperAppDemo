package com.clark.download.appdownload.content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.clark.download.appdownload.Config;


public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_DOWNLOAD = "create table "
            + Config.TABLE_NAME_TWO
            +"( id integer primary key,name text,size long)";

    public MySqliteHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DOWNLOAD);//创建数据表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
