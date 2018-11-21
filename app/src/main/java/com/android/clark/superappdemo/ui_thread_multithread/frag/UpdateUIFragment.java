package com.android.clark.superappdemo.ui_thread_multithread.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

public class UpdateUIFragment extends Fragment implements View.OnClickListener {
    private TextView textView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    /**
     * 解决handler内存泄漏的三种方法
     * 1.把 Handler 对象声明成静态的
     * 2.使用弱引用(WeakReference)
     * 3.在 onDestory() 的时候销毁 Handler 的对象；mHandler.removeCallbacksAndMessages(null);
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText("msg.what = " + msg.what + " 的时候 ： \n\n" + "msg.obj = " + msg.obj.toString());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_thread_update_ui, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.text_test);
        button1 = view.findViewById(R.id.btn_handler_post);
        button2 = view.findViewById(R.id.btn_on_ui_thread);
        button3 = view.findViewById(R.id.btn_view_post);
        button4 = view.findViewById(R.id.btn_handler_send);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_handler_post:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("使用post方法直接更新ui线程");
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn_on_ui_thread:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("使用runOnUiThread更新ui线程");
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn_view_post:
                button3.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("通过View的post方法更新ui");
                    }
                });
/*                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        button3.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("通过View的post方法更新ui");
                            }
                        });
                    }
                }).start();*/
                break;
            case R.id.btn_handler_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = mHandler.obtainMessage(7, "子线程中发布消息，更新主线程");
                        mHandler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
