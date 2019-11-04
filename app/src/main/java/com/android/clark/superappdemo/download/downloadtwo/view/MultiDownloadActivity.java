package com.android.clark.superappdemo.download.downloadtwo.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.appcompat.widget.DefaultItemAnimator;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import androidx.appcompat.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.download.downloadtwo.AppInfo;
import com.android.clark.superappdemo.download.downloadtwo.Config;
import com.android.clark.superappdemo.download.downloadtwo.content_provider.ProviderManager;
import com.android.clark.superappdemo.download.downloadtwo.down.DownloadInfo;
import com.android.clark.superappdemo.download.downloadtwo.down.MultiDownloadManager;
import com.android.clark.superappdemo.download.downloadtwo.message.DownloadInfoObserver;
import com.android.clark.superappdemo.download.downloadtwo.service.MultiDownloadService;
import com.android.clark.superappdemo.download.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiDownloadActivity extends Activity implements DownloadInfoObserver, View.OnClickListener {
    public static final String TAG = "MultiDownloadActivity:";
    public static final int UPDATE_PROGRESS = 1;
    public static final int UPDATE_BUTTON = 2;


    //绑定Service，实现Activity和Service通信
    private MultiDownloadService.DownloadBinder mServiceBinder;
    private boolean isBind = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "MultiDownloadService已连接");
            mServiceBinder = (MultiDownloadService.DownloadBinder) service;
//            mServiceBinder.setListener(MultiDownloadActivity.this);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBinder = null;
            isBind = false;
        }
    };

    /**
     * 以下为UI按钮被点击后控制service部分
     */
    private DownloadButtonClickListener downloadButtonClickListener = new DownloadButtonClickListener() {
        @Override
        public void onStartClick(AppInfo appInfo) {
            Log.i(TAG, "onStartClick");
            mServiceBinder.startDownload(appInfo);
        }

        @Override
        public void onPauseClick(AppInfo appInfo) {
            Log.i(TAG, "onPauseClick");
            mServiceBinder.pauseDownload(appInfo);
        }
    };



    private Button downloadedButton;
    private RecyclerView recy;

    private List<AppInfo> appInfos = new ArrayList<>();
    private MultiRecyAdapter adapter;

    private Handler uiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                case UPDATE_BUTTON:
//                    adapter.notifyDataSetChanged();
                    adapter.notifyItemChanged(msg.arg1, 1);//payload为空时，刷新整个页面
                    break;
                default:
                    break;
            }
        }
    };
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_download);
        //添加观察者，传递下载信息
        MultiDownloadManager.getInstance().addObserver(this);

        //绑定Service
        Intent serviceIntent = new Intent(this, MultiDownloadService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
        initDatas();
        initViews();
        Log.i(TAG,"onCreate执行了");
    }

    @Override
    protected void onDestroy() {
        if (isBind) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }

    private void initViews() {
        downloadedButton = findViewById(R.id.btn_downloaded);
        downloadedButton.setOnClickListener(this);

        recy = findViewById(R.id.download_recycler);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new MultiRecyAdapter(appInfos, downloadButtonClickListener, this);
        recy.setLayoutManager(manager);
        recy.setItemAnimator(new DefaultItemAnimator());
        //防止进度刷新时闪烁
        ((SimpleItemAnimator) recy.getItemAnimator()).setSupportsChangeAnimations(false);
        recy.setAdapter(adapter);
    }

    private void initDatas() {
        //从本地解析json数据
        String appInfoStr = Utils.getJson(this, "HomeListAppInfo.json");
        appInfos = new Gson().fromJson(appInfoStr, new TypeToken<List<AppInfo>>() {
        }.getType());

        //从下载文件夹中获取已下载或下载未完成的文件
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        final List<File> fileList = Utils.getFilesAllName(path);
        Log.i("------->", "fileList.size=" + fileList.size());

        if (!fileList.isEmpty() && fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                for (int j = 0; j < appInfos.size(); j++) {
                    AppInfo app = appInfos.get(j);
                    if (file.getName().equals(app.name + ".apk")) {
                        //设置appInfo的state
                        if (file.length() == ProviderManager.getInstance(this).query(app.id).size) {
                            appInfos.get(j).currentState = Config.DOWNLOAD_STATE_DOWNLOADED;
                        } else {
                            appInfos.get(j).currentState = Config.DOWNLOAD_STATE_PAUSED;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void notifyDownloadProgress(DownloadInfo downloadInfo) {
//        Log.i(TAG,"notifyDownloadProgress--更新进度条");
        for (int i = 0; i < appInfos.size(); i++) {
            if (appInfos.get(i).id == downloadInfo.appId) {
                appInfos.get(i).progress = downloadInfo.progress;

                Message msg = new Message();
                msg.arg1 = i;
                msg.what = UPDATE_PROGRESS;
                uiHandler.sendMessage(msg);
            }
        }
    }

    @Override
    public void notifyDownloadState(DownloadInfo downloadInfo) {
        Log.i(TAG, "notifyDownloadProgress--更新按钮显示状态");
        for (int i = 0; i < appInfos.size(); i++) {
            if (appInfos.get(i).id == downloadInfo.appId) {
                appInfos.get(i).currentState = downloadInfo.currentState;
                Message msg = new Message();
                msg.arg1 = i;
                msg.what = UPDATE_BUTTON;
                uiHandler.sendMessage(msg);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_downloaded:
                intent = new Intent(this, DownloadedActivity.class);
                startActivity(intent);
                break;
        }
    }
}
