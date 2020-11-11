package com.android.clark.superappdemo.ui_thread_multithread.frag;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.ui_thread_multithread.CustomIntentService;
import com.clark.common.base.frag.BaseFragment;

import java.util.concurrent.atomic.AtomicInteger;


public class IntentServiceFragment extends BaseFragment implements View.OnClickListener {

    private Intent serviceIntent;

    private Button btnStartService;
    private Button btnBindService;
    private Button btnUnbindService;
    private Button btnStopService;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_intent_service;
    }

    @Override
    protected void initView(View rootView) {
        btnStartService = (Button)rootView.findViewById( R.id.btn_start_service );
        btnBindService = (Button)rootView.findViewById( R.id.btn_bind_service );
        btnUnbindService = (Button)rootView.findViewById( R.id.btn_unbind_service );
        btnStopService = (Button)rootView.findViewById( R.id.btn_stop_service );
    }

    @Override
    protected void initData() {
        serviceIntent=new Intent(getActivity(), CustomIntentService.class);
        serviceIntent.putExtra("user_task","this_is_test_action");
    }

    @Override
    protected void initEvent() {
        btnStartService.setOnClickListener( this );
        btnBindService.setOnClickListener( this );
        btnUnbindService.setOnClickListener( this );
        btnStopService.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if ( v == btnStartService ) {
            mContext.startService(serviceIntent);
        } else if ( v == btnBindService ) {
            // Handle clicks for btnBindService
        } else if ( v == btnUnbindService ) {
            // Handle clicks for btnUnbindService
        } else if ( v == btnStopService ) {
            // Handle clicks for btnStopService
        }
    }
}
