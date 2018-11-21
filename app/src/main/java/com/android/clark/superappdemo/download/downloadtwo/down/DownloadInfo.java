package com.android.clark.superappdemo.download.downloadtwo.down;

import android.os.Environment;

import com.android.clark.superappdemo.download.downloadtwo.AppInfo;

public class DownloadInfo {
    public int appId;
    public String appName;
    public int progress;
    public int currentState;
    public String downloadUrl;
    public String saveUrl;

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "appId=" + appId +
                ", appName='" + appName + '\'' +
                ", progress=" + progress +
                ", currentState=" + currentState +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", saveUrl='" + saveUrl + '\'' +
                '}';
    }

    public static DownloadInfo clone(AppInfo appInfo) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.appId = appInfo.id;
        downloadInfo.appName = appInfo.name;
        downloadInfo.progress=0;
        downloadInfo.currentState = 0;
        downloadInfo.downloadUrl = appInfo.fileurl;
        downloadInfo.saveUrl = Environment.getExternalStorageDirectory() + "/" + appInfo.name + ".apk";
        return downloadInfo;
    }

}
