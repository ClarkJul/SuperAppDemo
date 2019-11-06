package com.clark.fourmodule.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.clark.fourmodule.R;

public class ServiceTestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG=ServiceTestActivity.class.getSimpleName();

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected=> name:"+name.toString());
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected=> name:"+name.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_service).setOnClickListener(this);
        findViewById(R.id.btn_unbind_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent serviceIntent = new Intent(this, TestService.class);
        int viewId=view.getId();
        if (viewId==R.id.btn_start_service){
            //开启Service
            startService(serviceIntent);
        }else if (viewId==R.id.btn_bind_service){
            //绑定Service
            bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
        }else if (viewId==R.id.btn_stop_service){
            //停止Service
            stopService(serviceIntent);
        }else if (viewId==R.id.btn_unbind_service){
            //解绑Service
            unbindService(mConnection);
        }
    }
}
