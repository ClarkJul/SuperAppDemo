package com.android.clark.superappdemo.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.clark.superappdemo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        goToMain();
    }

    private void goToMain() {
        Intent intent=new Intent();
        intent.setClass(SplashActivity.this,MainActivity.class);
//        intent.setClass(SplashActivity.this,Main2Activity.class);
        startActivity(intent);
        finish();
    }
}
