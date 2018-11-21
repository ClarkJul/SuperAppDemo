package com.android.clark.superappdemo.download.downloadtwo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.clark.superappdemo.MainActivity;
import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.download.downloadtwo.AppInfo;
import com.android.clark.superappdemo.download.downloadtwo.down.MultiDownloadManager;
import com.android.clark.superappdemo.download.downloadtwo.view.MultiDownloadActivity;

import java.util.ArrayList;

public class MultiDownloadService extends Service {

    private static final String TAG = "MultiDownloadService";
    private ArrayList<AppInfo> downloadApps;
    private MultiDownloadManager mDownloadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        if (mDownloadManager == null) {
            Log.d(TAG, "onCreate: init mDownloadManager");
            mDownloadManager = MultiDownloadManager.getInstance();
        }

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        stopForeground(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 处理UI按钮的点击事件
     */
    public class DownloadBinder extends Binder {

        public void startDownload(AppInfo appInfo) {
            Log.i(TAG, "startDownload");
            mDownloadManager.download(appInfo);
            getNotificationManager().notify(1, getNotification("正在下载：" + appInfo.name));
        }

        public void pauseDownload(AppInfo appInfo) {
            Log.i(TAG, "pauseDownload");
            mDownloadManager.pauseDownload(appInfo);
            getNotificationManager().notify(1, getNotification("暂停下载：" + appInfo.name));
        }
    }

    /**
     * 创建NotificationManager实例
     *
     * @return
     */
    private NotificationManager getNotificationManager() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "default", NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null, null);
            NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
            if (null != manager) {
                manager.createNotificationChannel(channel);
            }
            return manager;
        }
        return null;
    }

    private Notification getNotification(String title) {
        Intent intent = new Intent(this, MultiDownloadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "common");
        builder.setSmallIcon(R.mipmap.icon_start);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_start));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        builder.setChannelId("1");
        builder.setSound(null);

        return builder.build();
    }
}
