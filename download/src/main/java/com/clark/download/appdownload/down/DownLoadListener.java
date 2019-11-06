package com.clark.download.appdownload.down;

interface DownLoadListener {
    void onDownloadPause(DownloadInfo downloadInfo);

    void updateProgress(DownloadInfo downloadInfo, int progress);

    void onFail(DownloadInfo downloadInfo);

    void onCancel(DownloadInfo downloadInfo);

    void onFinish(DownloadInfo downloadInfo);
}
