package com.clark.download.appdownload.view;


import com.clark.download.appdownload.AppInfo;

public interface DownloadButtonClickListener {
    void onStartClick(AppInfo appInfo);
    void onPauseClick(AppInfo appInfo);
}
