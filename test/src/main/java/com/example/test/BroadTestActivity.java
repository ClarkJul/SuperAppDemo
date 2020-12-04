package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class BroadTestActivity extends AppCompatActivity {
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("BroadReceiver", "onReceive: BroadTestActivity");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_test);

        registerBroadcastReceiver();
    }

    /**
     * 接收静态的应用外的广播(无法接收)
     */
    private void registerBroadcastReceiver(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.clark.StaticBroadReceiver");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}