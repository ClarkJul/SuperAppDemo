package com.android.clark.superappdemo.download.downloadone;

public interface OnItemButtonClickListener {
    void onStartButtonClick(String url, boolean toStartDownload);
    void onCancelButtonClick(String url);
}
