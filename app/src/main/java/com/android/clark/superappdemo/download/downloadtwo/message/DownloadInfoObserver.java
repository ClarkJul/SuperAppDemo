package com.android.clark.superappdemo.download.downloadtwo.message;

import com.android.clark.superappdemo.download.downloadtwo.down.DownloadInfo;

public interface DownloadInfoObserver {
    void notifyDownloadProgress(DownloadInfo downloadInfo);

    void notifyDownloadState(DownloadInfo downloadInfo);
}
