package com.android.clark.superappdemo.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.util.Log;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.adapter.LeftAdapter;
import com.android.clark.superappdemo.main.adapter.RightAdapter;
import com.android.clark.superappdemo.main.bean.LeftBean;
import com.android.clark.superappdemo.main.bean.MainBean;
import com.android.clark.superappdemo.main.bean.RightBean;
import com.clark.common.Constant;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.utils.LocalJsonUtil;
import com.clark.common.utils.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG= Constant.TAG_HEADER+Main2Activity.class.getSimpleName();
    private RecyclerView rvLeft;
    private RecyclerView rvRight;

    private List<LeftBean> navigateList=new ArrayList<>();
    private  List<RightBean>  concreteList=new ArrayList<>();
    private final Map<Integer, Integer> indexMap = new HashMap<>();

    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();

    }

    private void initData() {
        getMainData();

        // 点击左侧需要知道对应右侧的位置，用map先保存起来
        for (int i = 0; i < concreteList.size(); i++) {
            if (concreteList.get(i).isHeader()) {
                indexMap.put(concreteList.get(i).position, i);
            }
        }
        // 左列表
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) rvLeft.getItemAnimator()).setSupportsChangeAnimations(false);
        leftAdapter = new LeftAdapter();
        leftAdapter.setListData(navigateList);
        rvLeft.setAdapter(leftAdapter);
        // 左侧列表的点击事件
        leftAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<LeftBean>() {
            @Override
            public void onItemClick(LeftBean item, int index) {
                // 左侧选中并滑到中间位置
                leftAdapter.setSelectedPosition(index);
                MyUtils.moveToMiddle(rvLeft, index);
                // 右侧滑到对应位置
                ((GridLayoutManager)rvRight.getLayoutManager()).scrollToPositionWithOffset(indexMap.get(index),0);
            }
        });
        // 右列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (concreteList.get(position).getType() == 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rvRight.setLayoutManager(gridLayoutManager);
        rightAdapter = new RightAdapter();
        rightAdapter.setListData(concreteList);
        rvRight.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<RightBean>() {
            @Override
            public void onItemClick(RightBean item, int index) {
               //跳转

            }
        });
        //右侧列表的滚动事件
        rvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //获取右侧列表的第一个可见Item的position
                int topPosition = ((GridLayoutManager) rvRight.getLayoutManager()).findFirstVisibleItemPosition();
                // 如果此项对应的是左边的大类的index
                if (concreteList.get(topPosition).isHeader()) {
                    MyUtils.moveToMiddle(rvLeft, concreteList.get(topPosition).position);
                    leftAdapter.setSelectedPosition(concreteList.get(topPosition).position);
                }

            }
        });
    }

    private void initView() {
        rvLeft = findViewById(R.id.rv_left);
        rvRight = findViewById(R.id.rv_right);
    }

    void getMainData(){
        if (navigateList!=null){
            navigateList.clear();
        }else navigateList=new ArrayList<>();
        if (concreteList!=null) concreteList.clear();
        else concreteList=new ArrayList<>();

        String result= LocalJsonUtil.getJson(Main2Activity.this,"main.json");
        List<MainBean> allBean = new Gson().fromJson(result, new TypeToken<List<MainBean>>() {
        }.getType());
        if (allBean!=null){
            for (int i=0;i<allBean.size();i++){
                String header=allBean.get(i).getHeader();

                LeftBean leftBean=new LeftBean(header);
                navigateList.add(leftBean);

                RightBean headerBean=new RightBean(header,0);
                headerBean.setHeader(true);
                headerBean.position=i;
                concreteList.add(headerBean);

                List<MainBean.DataBean> childList=allBean.get(i).getData();
                if (childList!=null){
                    for (int j=0;j<childList.size();j++){
                        RightBean child=new RightBean(childList.get(j).getChild(),1);
                        child.setHeader(false);
                        child.position=-1;
                        concreteList.add(child);
                    }
                }
            }
        }else {
            Log.d(TAG, "getMainData=> main data is null");
        }
    }
}
