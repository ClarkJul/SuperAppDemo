package com.clark.download.appdownload.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.clark.download.appdownload.Config;


public class DownloadContentProvider extends ContentProvider {
    private MySqliteHelper myDBhelper;
    private static final String TAG = "MyContentProvider";

    //添加整形常量
    public static final int USER_DIR = 0;
    public static final int USER_ITEM = 1;
    //创建authority
    public static final String AUTHORITY = "com.android.clark.superappdemo.provider";
    //创建UriMatcher对象
    private static UriMatcher uriMatcher;

    //创建静态代码块
    static {
        //实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //可以实现匹配URI的功能
        //参数1：authority 参数2：路径 参数3：自定义代码
        uriMatcher.addURI(AUTHORITY, Config.TABLE_NAME_TWO, USER_DIR);
        uriMatcher.addURI(AUTHORITY, Config.TABLE_NAME_TWO + "/#", USER_ITEM);
    }

    public DownloadContentProvider() {
        Log.e(TAG, "MyContentProvider: ");
    }

    @Override
    public boolean onCreate() {
        try {
            //实现创建MyDBHelpter对象
            myDBhelper = new MySqliteHelper(getContext(), Config.DATABASE_NAME, 1);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myDBhelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                //参数1：表名  其他参数可借鉴上面的介绍
                cursor = db.query(Config.TABLE_NAME_TWO, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ITEM:
                String queryId = uri.getPathSegments().get(1);
                cursor = db.query(Config.TABLE_NAME_TWO, projection, "id=?", new String[]{queryId}, null, null, sortOrder);
                break;
            default:
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myDBhelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
            case USER_ITEM:
                //参数1：表名  参数2：没有赋值的设为空   参数3：插入值
                long newUserId = db.insert(Config.TABLE_NAME_TWO, null, values);
                uriReturn=Uri.parse("Content://"+AUTHORITY+"/"+Config.TABLE_NAME_TWO+"/"+newUserId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //创建SQLiteDatabase对象
        SQLiteDatabase db = myDBhelper.getWritableDatabase();
        int deleteInt = 0;
        //匹配uri
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                //参数1：表名   参数2：约束删除列的名字   参数3：具体行的值
                deleteInt = db.delete(Config.TABLE_NAME_TWO, selection, selectionArgs);
                break;
            case USER_ITEM:
                String deleteId = uri.getPathSegments().get(1);
                deleteInt = db.delete(Config.TABLE_NAME_TWO, "id=?", new String[]{deleteId});
                break;
            default:
        }
        return deleteInt;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myDBhelper.getWritableDatabase();
        int updateRow = 0;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                updateRow = db.update(Config.TABLE_NAME_TWO,values,selection,selectionArgs);
                break;
            case USER_ITEM:
                String updateId = uri.getPathSegments().get(1);
                updateRow = db.update(Config.TABLE_NAME_TWO,values,"id=?",new String[]{updateId});
                break;
            default:
        }
        return updateRow;
    }
}
