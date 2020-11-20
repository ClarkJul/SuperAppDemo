package com.clark.aidl_server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.clark.aidl_server.aidl.Fruit;
import com.clark.aidl_server.aidl.FruitManager;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplayFruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplayFruit = findViewById(R.id.tv_display_fruit);
    }

    public void onAddFruit(View view) {
        FruitManager.getInstance().addFruit(new Fruit("苹果","红色"));
        FruitManager.getInstance().addFruit(new Fruit("香蕉","黄色"));

    }

    public void onDisplayFruit(View view) {
        tvDisplayFruit.setText(new Gson().toJson(FruitManager.getInstance().getAllFruit()));
    }
}