package com.android.clark.superappdemo.ui_thread_multithread;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomIntentService extends IntentService {

    private static final String TAG = CustomIntentService.class.getSimpleName();
    private AtomicInteger count=new AtomicInteger(0);
    public CustomIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ======>" +TAG);
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ======>" +TAG);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String userAction=intent.getStringExtra("user_task");
        Log.e(TAG, "onHandleIntent: userAction="+userAction+" ,count="+count.getAndIncrement());
        Log.e(TAG, "onHandleIntent: currentThread="+Thread.currentThread() );
        SystemClock.sleep(5000);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ======>" +TAG);
        super.onDestroy();
    }
}