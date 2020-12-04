package com.example.test.static_broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.example.test.BroadTestActivity;

public class StaticBroadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"收到来自外星的广播",Toast.LENGTH_SHORT).show();
        Log.e("StaticBroadReceiver", "onReceive: 收到来自外星的广播" );
        Intent intent1 = new Intent(context, BroadTestActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
