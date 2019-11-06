package com.android.clark.superappdemo;

import android.app.Application;
import android.content.Context;

import com.clark.common.CommonModule;
import com.clark.download.DownloadModule;

public class SupperApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CommonModule.init(context);
        DownloadModule.init(context);
    }

    public static Context getContext() {
        return context;
    }
}
