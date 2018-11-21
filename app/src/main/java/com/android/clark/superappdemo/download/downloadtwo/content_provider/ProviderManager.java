package com.android.clark.superappdemo.download.downloadtwo.content_provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.clark.superappdemo.download.downloadtwo.Config;

import java.util.ArrayList;
import java.util.List;

public class ProviderManager {
    private static final String TAG = "ProviderManager";
    private static ProviderManager mInstance;

    private Context context;

    private ProviderManager(Context context) {
        this.context = context;
    }

    public static ProviderManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ProviderManager.class) {
                if (mInstance == null) {
                    mInstance = new ProviderManager(context);
                }
            }
        }
        return mInstance;
    }

    public List<ProviderBean> query() {
        Uri uri = Uri.parse(Config.PROVIDER + "/" + Config.TABLE_NAME_TWO);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        List<ProviderBean> beanList = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ProviderBean bean = new ProviderBean();
                bean.id = cursor.getInt(cursor.getColumnIndex("id"));
                bean.name = cursor.getString(cursor.getColumnIndex("name"));
                bean.size = cursor.getLong(cursor.getColumnIndex("size"));
                beanList.add(bean);
                cursor.moveToNext();
                Log.d(TAG, bean.toString());
            }
            cursor.close();
        }
        return beanList;
    }

    public ProviderBean query(int id){
        Uri uri = Uri.parse(Config.PROVIDER + "/" + Config.TABLE_NAME_TWO+"/"+id);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        ProviderBean bean = new ProviderBean();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bean.id = cursor.getInt(cursor.getColumnIndex("id"));
                bean.name = cursor.getString(cursor.getColumnIndex("name"));
                bean.size = cursor.getLong(cursor.getColumnIndex("size"));
                cursor.moveToNext();
                Log.d(TAG, bean.toString());
            }
            cursor.close();
        }
        return bean;
    }

    /**
     * 插入数据到provider
     */
    public void insertData(ProviderBean bean) {
        List<ProviderBean> list;
        list = query();
        boolean isSave = false;//保存的文件大小信息到contentProvider的标志位
        if (bean != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).id == bean.id) {
                    isSave = true;
                    break;
                }
            }
            //如果没有保存过，就插入到数据库中
            if (!isSave) {
                Uri uri = Uri.parse(Config.PROVIDER + "/" + Config.TABLE_NAME_TWO);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", bean.id);
                contentValues.put("name", bean.name);
                contentValues.put("size", bean.size);
                context.getContentResolver().insert(uri, contentValues);
                Log.i(TAG,bean.toString());
            }
        }
    }

    /**
     * 更改数据
     */
    public void updateData(ProviderBean bean, int updateId) {
        if (bean != null) {
            Uri uri = Uri.parse(Config.PROVIDER + "/" + Config.TABLE_NAME_TWO + "/" + updateId);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", bean.id);
            contentValues.put("name", bean.name);
            contentValues.put("size", bean.size);
            context.getContentResolver().update(uri, contentValues, null, null);
        }
    }

    /**
     * 删除数据
     */
    public void delateData(int deleteId) {
        Uri uri = Uri.parse(Config.PROVIDER + "/" + Config.TABLE_NAME_TWO + "/" + deleteId);
        context.getContentResolver().delete(uri, null, null);
    }


}
