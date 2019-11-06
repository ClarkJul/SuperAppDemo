package com.clark.fourmodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
    private static final String TAG= TestService.class.getSimpleName();
    public TestService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate=> currentThread:"+Thread.currentThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand=>currentThread:"+Thread.currentThread());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind=>currentThread:"+Thread.currentThread());
//        throw new UnsupportedOperationException("Not yet implemented");
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind=> currentThread:"+Thread.currentThread());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy=> currentThread:"+Thread.currentThread());
        super.onDestroy();
    }
}
