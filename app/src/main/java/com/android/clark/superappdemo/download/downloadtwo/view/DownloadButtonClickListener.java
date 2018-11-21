package com.android.clark.superappdemo.download.downloadtwo.view;

import com.android.clark.superappdemo.download.downloadtwo.AppInfo;

public interface DownloadButtonClickListener {
    void onStartClick(AppInfo appInfo);
    void onPauseClick(AppInfo appInfo);
}
