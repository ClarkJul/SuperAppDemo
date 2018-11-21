package com.android.clark.superappdemo.download.downloadtwo.message;


import com.android.clark.superappdemo.download.downloadtwo.down.DownloadInfo;


public interface DownloadInfoPublisher {
    void addObserver(DownloadInfoObserver downloadInfoObserver);

    void removeObserver(DownloadInfoObserver downloadInfoObserver);

    void notifyObserversOnProgressChange(DownloadInfo downloadInfo);

    void notifyObserversOnStateChange(DownloadInfo downloadInfo);
}
