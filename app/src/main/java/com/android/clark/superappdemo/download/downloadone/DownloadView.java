package com.android.clark.superappdemo.download.downloadone;


public interface DownloadView {

    void onDownloadPause(String url);

    void updateProgress(String url, int progress);

    void onFail(String url);

    void onCancel(String url);
}
