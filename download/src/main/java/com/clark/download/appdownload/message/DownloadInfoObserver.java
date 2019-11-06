package com.clark.download.appdownload.message;


import com.clark.download.appdownload.down.DownloadInfo;

public interface DownloadInfoObserver {
    void notifyDownloadProgress(DownloadInfo downloadInfo);

    void notifyDownloadState(DownloadInfo downloadInfo);
}
