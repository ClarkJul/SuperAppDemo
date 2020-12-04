package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.test.static_broad.StaticBroadReceiver;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("StaticBroadReceiver", "onReceive: MainActivity");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver(){
        IntentFilter intentFilter=new IntentFilter();
//        intentFilter.addAction("com.clark.StaticBroadReceiver");
        intentFilter.addAction("com.android.clark.superappdemo.onSendDylBroadClick");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}