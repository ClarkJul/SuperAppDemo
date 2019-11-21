package com.clark.custom_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.clark.custom_view.bean.BlogBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomViewActivity extends AppCompatActivity {


    private TextView tvBlog;
    private TextView tvExample;
    private RecyclerView rvBlog;
    private RecyclerView rvExample;

    private Map<String,String> exampleNameList;
    private List<BlogBean> blogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        initView();
        initData();
    }

    private void initData() {
        exampleNameList=new HashMap<>();
        blogList=new ArrayList<>();
    }

    private void initView() {
        tvBlog=findViewById(R.id.tv_blog);
        tvExample=findViewById(R.id.tv_example);
        rvBlog=findViewById(R.id.rv_blog_container);
        rvExample=findViewById(R.id.rv_example_container);
    }
}
