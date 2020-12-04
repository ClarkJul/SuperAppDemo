package com.clark.fourmodule.broadcast.static_broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StaticBroadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("StaticBroadReceiver", "onReceive: com.clark.fourmodule" );
    }
}
