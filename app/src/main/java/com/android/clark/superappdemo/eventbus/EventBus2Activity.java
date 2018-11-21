package com.android.clark.superappdemo.eventbus;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

import org.greenrobot.eventbus.EventBus;

public class EventBus2Activity extends Activity {
    private ImageView imageView;
    private TextView textName;
    private TextView textDesc;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus2);
        initViews();
    }
    private void initViews(){
        imageView=findViewById(R.id.img_display);
        textName=findViewById(R.id.text_display1);
        textDesc=findViewById(R.id.text_display2);

        button1=findViewById(R.id.btn_send_msg);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectMessage om=new ObjectMessage();
                om.imageView=(imageView.getBackground());
                om.textName=textName.getText().toString();
                om.textDesc=textDesc.getText().toString();
                EventBus.getDefault().post(om);
                finish();
            }
        });

    }
}
