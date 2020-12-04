package com.clark.fourmodule.broadcast.simple;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clark.fourmodule.R;


public class BroadcastTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        Button offline=findViewById(R.id.btn_force_offline);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.android.clark.superappdemo.broadcast.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }

    public void onSendStaticBroadClick(View view) {
        //8.0以上 静态广播 以这种方式发送广播
        Intent intent = new Intent("com.example.test.StaticBroadReceiver");
        intent.setComponent(new ComponentName("com.example.test","com.example.test.static_broad.StaticBroadReceiver"));
        sendBroadcast(intent,null);
    }

    public void onSendAllBroadClick(View view) {
/*        Intent intent = new Intent("com.clark.StaticBroadReceiver");
        intent.setComponent(new ComponentName("com.android.clark.superappdemo","com.clark.fourmodule.broadcast.static_broad.StaticBroadReceiver"));
        sendBroadcast(intent,null);*/


        sendBroadcast(new Intent("com.clark.StaticBroadReceiver"));
    }

    public void onSendDylBroadClick(View view) {
        String broadcast="com.android.clark.superappdemo.onSendDylBroadClick";
        Intent intent = new Intent(broadcast);
        sendBroadcast(intent);
    }
}
