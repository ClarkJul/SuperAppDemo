package com.android.clark.superappdemo.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBUS1Activity extends Activity implements View.OnClickListener {

    private ImageView img;
    private TextView textName;
    private TextView textDesc;
    private TextView textMain;
    private Button button1;
    private Button button2;
    private Button button3;
    private StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus1);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initViews();
    }

    private void initViews() {
        img = findViewById(R.id.img_display);
        textName = findViewById(R.id.text_display1);
        textDesc = findViewById(R.id.text_display2);
        textMain = findViewById(R.id.text_display3);

        button1 = findViewById(R.id.btn_send_msg1);
        button2 = findViewById(R.id.btn_send_msg2);
        button3 = findViewById(R.id.btn_send_enter);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        sb = new StringBuffer();
    }

    /**
     * 接收EventBus发送的消息
     * @param msg
     *
     * 该方法必须为public
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object msg) {
        if (msg instanceof StringMessage) {
            String result = ((StringMessage) msg).message;
            sb.append(result + "\n");
            textMain.setText(sb.toString());
        } else if (msg instanceof ObjectMessage) {
            Log.i("EventBus","收到对象消息");
            ObjectMessage om = (ObjectMessage) msg;
            img.setBackground(om.imageView);
            textName.setText(om.textName);
            textDesc.setText(om.textDesc);
            sb.append(om.textDesc+"\n");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_msg1:
                StringMessage message = new StringMessage();
                message.message = "我是本页主线程的消息";
                EventBus.getDefault().post(message);
                break;
            case R.id.btn_send_msg2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            StringMessage message = new StringMessage();
                            message.message = "我是本页子线程的消息";
                            EventBus.getDefault().post(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_send_enter:
                Intent intent = new Intent(EventBUS1Activity.this, EventBus2Activity.class);
                startActivity(intent);
                break;
        }
    }


}
