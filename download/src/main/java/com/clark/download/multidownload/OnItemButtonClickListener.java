package com.clark.download.multidownload;

public interface OnItemButtonClickListener {
    void onStartButtonClick(String url, boolean toStartDownload);
    void onCancelButtonClick(String url);
}
