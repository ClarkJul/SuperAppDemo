package com.android.clark.superappdemo.download.downloadone;

/**
 * Created by Luckychuan on 2018/3/14.
 */

public abstract class DownloadModel {
    abstract void addDownloadTask(String url);
    abstract void pauseDownload(String url);
    abstract void cancelDownload(String url);
}
