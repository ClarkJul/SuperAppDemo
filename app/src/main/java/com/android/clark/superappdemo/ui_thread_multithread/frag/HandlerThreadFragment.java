package com.android.clark.superappdemo.ui_thread_multithread.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

/**
 * 两秒更新一次UI
 */
public class HandlerThreadFragment extends Fragment {

    private TextView textView;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    //UI线程的Handler
    private Handler mainHandler = new Handler();

    //以防退出界面后Handler还在执行
    private boolean isUpdateInfo;
    //用以表示该handler的常数
    private static final int MSG_UPDATE_INFO = 0x110;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_thread_handlerthread, container, false);
        textView = view.findViewById(R.id.handler_thread_text);
        initThread();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isUpdateInfo = true;
        mHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    public void onPause() {
        super.onPause();
        isUpdateInfo = false;
        mHandler.removeMessages(MSG_UPDATE_INFO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        mHandlerThread.quit();
    }

    private void initThread() {
        mHandlerThread = new HandlerThread("handlerThread-test");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            public void handleMessage(Message msg) {
                try {
                    Thread.sleep(2000);
                    //子线程不能更新UI，创建Handler更新UI
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String result = "每隔2秒更新一下数据：";
                            result += Math.random();
                            textView.setText(result);
                        }
                    });
                    if (isUpdateInfo) {
                        mHandler.sendEmptyMessage(MSG_UPDATE_INFO);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
