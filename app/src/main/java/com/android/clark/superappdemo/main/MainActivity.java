package com.android.clark.superappdemo.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.banner.BannerActivity;
import com.clark.custom_view.CustomControlActivity;
import com.clark.custom_view.animation.AnimationActivity;
import com.clark.custom_view.draw_picture.DrawPictureActivity;
import com.clark.fourmodule.broadcast.simple.LoginActivity;
import com.android.clark.superappdemo.contentprovider.ContentProviderActivity;
import com.android.clark.superappdemo.eventbus.EventBUS1Activity;
import com.android.clark.superappdemo.experiment.AdapterActivity;
import com.android.clark.superappdemo.httpurlconnection.ClientConnectionActivity;
import com.android.clark.superappdemo.httpurlconnection.HttpUrlConnectionActivity;
import com.android.clark.superappdemo.jiexixml.XmlActivity;
import com.android.clark.superappdemo.ui_thread_multithread.UIMultiThreadActivity;
import com.clark.download.appdownload.view.MultiDownloadActivity;
import com.clark.download.multidownload.DownloadActivity;
import com.clark.fourmodule.service.ServiceTestActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity implements View.OnClickListener {


    //四大组件
    private Button broadButtton;
    private Button serviceButton;

    private Button getConnectionButtton;
    private Button postConnectionButtton;
    private Button contentProviderButtton;
    private Button xmlButtton;
    private Button downloadButtton;
    private Button mDownloadButtton;
    private Button adaButtton;
    private Button animationButtton;
    private Button updateUIButtton;
    private Button eventBusButtton;
    private Button bannerButtton;
    private Button customControlButtton;
    private Button drawPicButtton;
    private Button viewPicButtton;

    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getPermission();//申请权限
    }

    private void initViews() {
        getConnectionButtton = findViewById(R.id.btn_http_url_connection_get);
        postConnectionButtton = findViewById(R.id.btn_http_url_connection_post);
        contentProviderButtton = findViewById(R.id.btn_content_provider);
        xmlButtton = findViewById(R.id.btn_xml);
        downloadButtton = findViewById(R.id.btn_service_one);
        mDownloadButtton = findViewById(R.id.btn_service_two);
        adaButtton = findViewById(R.id.btn_experiment_adapter);
        animationButtton = findViewById(R.id.btn_animation);
        updateUIButtton = findViewById(R.id.btn_update_ui);
        broadButtton = findViewById(R.id.btn_broadcast);
        eventBusButtton = findViewById(R.id.btn_event_bus);
        bannerButtton = findViewById(R.id.btn_banner);
        customControlButtton=findViewById(R.id.btn_custom_control);
        drawPicButtton=findViewById(R.id.btn_draw_pic);
        viewPicButtton=findViewById(R.id.btn_view);
        serviceButton=findViewById(R.id.btn_service);

        getConnectionButtton.setOnClickListener(this);
        postConnectionButtton.setOnClickListener(this);
        contentProviderButtton.setOnClickListener(this);
        xmlButtton.setOnClickListener(this);
        downloadButtton.setOnClickListener(this);
        mDownloadButtton.setOnClickListener(this);
        adaButtton.setOnClickListener(this);
        animationButtton.setOnClickListener(this);
        updateUIButtton.setOnClickListener(this);
        broadButtton.setOnClickListener(this);
        eventBusButtton.setOnClickListener(this);
        bannerButtton.setOnClickListener(this);
        customControlButtton.setOnClickListener(this);
        drawPicButtton.setOnClickListener(this);
        viewPicButtton.setOnClickListener(this);
        serviceButton.setOnClickListener(this);
    }
    private void getPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经申请过权限，做想做的事
            Log.i("MainActivity","有权限");
        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "申请权限", 1,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_http_url_connection_get:
                Toast.makeText(this, "click getConnectionButtton", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, HttpUrlConnectionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_http_url_connection_post:
                Toast.makeText(this, "click postConnectionButtton", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ClientConnectionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_content_provider:
                intent = new Intent(MainActivity.this, ContentProviderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_xml:
                intent = new Intent(MainActivity.this, XmlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_service_one:
                intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_service_two:
                intent = new Intent(MainActivity.this, MultiDownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_experiment_adapter:
                intent = new Intent(MainActivity.this, AdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_animation:
                intent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update_ui:
                intent = new Intent(MainActivity.this, UIMultiThreadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_broadcast:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_event_bus:
                intent = new Intent(MainActivity.this, EventBUS1Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_banner:
                intent = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_custom_control:
                intent = new Intent(MainActivity.this, CustomControlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_draw_pic:
                intent = new Intent(MainActivity.this, DrawPictureActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view:
//                intent = new Intent(MainActivity.this, CustomControlActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_service:
                intent = new Intent(MainActivity.this, ServiceTestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
