package com.clark.download.appdownload.down;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.clark.download.appdownload.AppInfo;
import com.clark.download.appdownload.Config;
import com.clark.download.appdownload.message.DownloadInfoObserver;
import com.clark.download.appdownload.message.DownloadInfoPublisher;
import com.clark.download.utils.InstallUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MultiDownloadManager implements DownloadInfoPublisher {
    public static final String TAG="MultiDownloadManager";

    public static MultiDownloadManager instance = null;

    public static MultiDownloadManager getInstance() {
        if (instance == null) {
            instance = new MultiDownloadManager();
        }
        return instance;
    }

    public static Map<Integer, DownloadInfo> downloadInfoMap = new HashMap<>();
    public static Map<Integer, DownloadTask> downloadTaskMap = new HashMap<>();
    public static List<DownloadInfoObserver> downloadInfoObservers = new ArrayList<>();

    private static ThreadPoolExecutor mDownloadPool=
            new ThreadPoolExecutor(2, 2, 5, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory());

    private DownLoadListener mDownLoadListener = new DownLoadListener() {
        @Override
        public void onDownloadPause(DownloadInfo downloadInfo) {

        }

        @Override
        public void updateProgress(DownloadInfo downloadInfo, int progress) {
            if (downloadInfo.currentState!= Config.DOWNLOAD_STATE_DOWNLOADING){
                downloadInfo.currentState = Config.DOWNLOAD_STATE_DOWNLOADING;
                notifyObserversOnStateChange(downloadInfo);
            }
//            Log.i("----------->","updateProgress:state="+downloadInfo.currentState);
            downloadInfo.progress = progress;
            notifyObserversOnProgressChange(downloadInfo);
        }

        @Override
        public void onFail(DownloadInfo downloadInfo) {
            downloadInfo.currentState = Config.DOWNLOAD_STATE_ERROR;
            notifyObserversOnStateChange(downloadInfo);
            if (downloadInfoMap.containsKey(downloadInfo.appId)){
                downloadInfoMap.remove(downloadInfo.appId);
            }
            if (downloadTaskMap.containsKey(downloadInfo.appId)){
                downloadTaskMap.remove(downloadInfo.appId);
            }
        }

        @Override
        public void onCancel(DownloadInfo downloadInfo) {

        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            downloadInfo.currentState = Config.DOWNLOAD_STATE_DOWNLOADED;
            notifyObserversOnStateChange(downloadInfo);
            if (downloadInfoMap.containsKey(downloadInfo.appId)){
                downloadInfoMap.remove(downloadInfo.appId);
            }
            if (downloadTaskMap.containsKey(downloadInfo.appId)){
                downloadTaskMap.remove(downloadInfo.appId);
            }
        }
    };

    public void download(final AppInfo appInfo) {
        Log.i(TAG,"download:开始下载");
        DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.id);
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.clone(appInfo);
            downloadInfoMap.put(appInfo.id, downloadInfo);
        }
        Log.i(TAG,"downloadInfo.currentState="+downloadInfo.currentState);
        if (downloadInfo.currentState == Config.DOWNLOAD_STATE_START || downloadInfo.currentState == Config.DOWNLOAD_STATE_PAUSED || downloadInfo.currentState == Config.DOWNLOAD_STATE_ERROR) {
            downloadInfo.currentState = Config.DOWNLOAD_STATE_WAITING;
            Log.i(TAG,"download:满足下载条件");
            DownloadTask downloadTask = new DownloadTask(downloadInfo, mDownLoadListener);
            downloadTaskMap.put(downloadInfo.appId, downloadTask);
            downloadTask.executeOnExecutor(mDownloadPool);
//            downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            notifyObserversOnStateChange(downloadInfo);
        }
    }

    public void pauseDownload(AppInfo appInfo) {
        Log.i(TAG,"pauseDownload:暂停下载");
        DownloadInfo downloadInfo = downloadInfoMap.remove(appInfo.id);
        if (downloadInfo != null) {
            downloadInfo.currentState = Config.DOWNLOAD_STATE_PAUSED;
            notifyObserversOnStateChange(downloadInfo);
        }
        DownloadTask downloadTask = downloadTaskMap.remove(appInfo.id);
        if (downloadTask != null) {
            downloadTask.setPause();
            if (!downloadTask.isCancelled()){
                downloadTask.cancel(true);
            }
        }
    }

    public void cancelDownload(AppInfo appInfo) {

    }

    public void install(AppInfo appInfo, Context context) {
        DownloadInfo downloadInfo = DownloadInfo.clone(appInfo);
        String apkUri= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+downloadInfo.appName+".apk";
        Log.i("----------->",apkUri);
        InstallUtil.install(context,apkUri,false);
    }

    @Override
    public void addObserver(DownloadInfoObserver downloadInfoObserver) {
        downloadInfoObservers.add(downloadInfoObserver);
    }

    @Override
    public void removeObserver(DownloadInfoObserver downloadInfoObserver) {
        downloadInfoObservers.remove(downloadInfoObserver);
    }

    @Override
    public void notifyObserversOnProgressChange(DownloadInfo downloadInfo) {
        for (DownloadInfoObserver downloadInfoObserver : downloadInfoObservers) {
            downloadInfoObserver.notifyDownloadProgress(downloadInfo);
        }
    }

    @Override
    public void notifyObserversOnStateChange(DownloadInfo downloadInfo) {
        for (DownloadInfoObserver downloadInfoObserver : downloadInfoObservers) {
            downloadInfoObserver.notifyDownloadState(downloadInfo);
        }
    }


    //没有下载完成，保存进度到数据库，
    public void saveProgress() {

    }
}
