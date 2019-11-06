package com.clark.download.multidownload;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import com.clark.download.R;

import java.util.List;

public class DownloadActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DownloadActivity";
    private static final String URL1 = "http://m.down.sandai.net/MobileThunder/Android_5.34.2.4700/XLWXguanwang.apk";
    private static final String URL2 = "http://s1.music.126.net/download/android/CloudMusic_official_4.0.0_179175.apk";

    private static RecyclerAdapter mAdapter;

    //绑定Service，实现Activity和Service通信
    private DownloadService.DownloadBinder mServiceBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceBinder = (DownloadService.DownloadBinder) service;
            mServiceBinder.setListener(onTaskDataChangeListener);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBinder = null;
        }
    };
    /**
     * 以下为UI按钮被点击后控制service部分
     */
    private OnItemButtonClickListener onItemButtonClickListener = new OnItemButtonClickListener() {
        @Override
        public void onStartButtonClick(String url, boolean toStartDownload) {
            if (toStartDownload) {
                mServiceBinder.startDownload(url);
            } else {
                mServiceBinder.pauseDownload(url);
            }
        }

        @Override
        public void onCancelButtonClick(String url) {
            mServiceBinder.cancelDownload(url);
        }
    };
    /**
     * 以下为当Service中数据变化时回调刷新RecyclerView的数据
     *
     * @param list
     */
    private DownloadService.OnTaskDataChangeListener onTaskDataChangeListener =
            new DownloadService.OnTaskDataChangeListener() {
                @Override
                public void onInitFinish(List<Task> list) {
                    //初始化RecyclerView
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DownloadActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    //防止进度刷新时闪烁
                    ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    mAdapter = new RecyclerAdapter(list, onItemButtonClickListener);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onDataInsert(int position) {
                    mAdapter.notifyItemInserted(position);
                }

                @Override
                public void onDataChange(int position) {
                    mAdapter.notifyItemChanged(position);
                }

                @Override
                public void onDataRemove(int position) {
                    mAdapter.notifyItemRemoved(position);
                }
            };
    /**
     * 以下为获取读写权限，建立Activity与Service通信连接,初始化UI部分
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        //绑定Service
        Intent serviceIntent = new Intent(this, DownloadService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);

        //获取权限
        int readStorageCheck = ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStorageCheck == PackageManager.PERMISSION_GRANTED) {
            initUI();
        } else {
            ActivityCompat.requestPermissions(DownloadActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //当权限获得时
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initUI();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DownloadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //弹出对话框提示用户接收权限
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("程序要获得权限后才能运行");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求读取手机存储的权限
                        ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    }
                });
                dialog.create().show();
            }
        }
    }

    private void initUI() {
        Button button = (Button) findViewById(R.id.new_task_btn);
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.new_task2_btn);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.stop_service);
        button3.setOnClickListener(this);
        Button button4 = (Button) findViewById(R.id.finish_activity);
        button4.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.new_task_btn) {
            mServiceBinder.newTask(URL1);
        } else if (id == R.id.new_task2_btn) {
            mServiceBinder.newTask(URL2);
        } else if (id == R.id.stop_service) {
            mServiceBinder.saveProgress();
            stopService(new Intent(this, DownloadService.class));
            finish();
        } else if (id == R.id.finish_activity) {
            finish();
        }
    }
}
