package com.android.clark.superappdemo.experiment;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.clark.superappdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterActivity extends Activity implements View.OnClickListener {

    private List<String> mDatas;
    private RecyclerView recyclerView;
    private AdaAdapter adapter;
    private Button addButton;
    private Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        initDatas();
        initViews();
        initListener();
    }

    private void initViews() {
        addButton = findViewById(R.id.btn_add);
        delButton = findViewById(R.id.btn_delete);
        recyclerView = findViewById(R.id.recy_adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new AdaAdapter(this, mDatas);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        addButton.setOnClickListener(this);
        delButton.setOnClickListener(this);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("------" + i + "------");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mDatas.add("oooooooo");
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                mDatas.remove(mDatas.size() - 1);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
