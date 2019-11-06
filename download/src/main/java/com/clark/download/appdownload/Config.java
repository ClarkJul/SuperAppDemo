package com.clark.download.appdownload;

public class Config {
    public static final int DOWNLOAD_STATE_START=0;
    public static final int DOWNLOAD_STATE_DOWNLOADING=1;
    public static final int DOWNLOAD_STATE_PAUSED=2;
    public static final int DOWNLOAD_STATE_WAITING=3;
    public static final int DOWNLOAD_STATE_DOWNLOADED=4;
    public static final int DOWNLOAD_STATE_ERROR=5;


    //数据库
    public static final String DATABASE_NAME="Download.db";
    public static final String TABLE_NAME_TWO="downloadinfo";
    public static final String PROVIDER = "content://com.android.clark.superappdemo.provider";
}
