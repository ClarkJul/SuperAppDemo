package com.clark.fourmodule.broadcast.simple;

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
}
