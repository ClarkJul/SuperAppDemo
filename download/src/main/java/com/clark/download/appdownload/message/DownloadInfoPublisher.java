package com.clark.download.appdownload.message;


import com.clark.download.appdownload.down.DownloadInfo;

public interface DownloadInfoPublisher {
    void addObserver(DownloadInfoObserver downloadInfoObserver);

    void removeObserver(DownloadInfoObserver downloadInfoObserver);

    void notifyObserversOnProgressChange(DownloadInfo downloadInfo);

    void notifyObserversOnStateChange(DownloadInfo downloadInfo);
}
