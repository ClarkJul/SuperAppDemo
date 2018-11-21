package com.android.clark.superappdemo.ui_thread_multithread;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.custom_control.animation.MyPagerAdapter;
import com.android.clark.superappdemo.ui_thread_multithread.frag.ThreeFragment;
import com.android.clark.superappdemo.ui_thread_multithread.frag.HandlerThreadFragment;
import com.android.clark.superappdemo.ui_thread_multithread.frag.UpdateUIFragment;

import java.util.ArrayList;
import java.util.List;

public class UIMultiThreadActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private List<Fragment> fragments;


    private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_multi_thread);
        initViews();
        initListener();
        initDatas();
    }


    private void initViews() {
        mViewPager = findViewById(R.id.thread_view_pager);

        tv_item_one = findViewById(R.id.tv_item_one);
        tv_item_two = findViewById(R.id.tv_item_two);
        tv_item_three = findViewById(R.id.tv_item_three);

    }

    private void initDatas() {
        fragments = new ArrayList<>();
        fragments.add(new UpdateUIFragment());
        fragments.add(new HandlerThreadFragment());
        fragments.add(new ThreeFragment());

        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);  //初始化显示第一个页面
        dragSet(Color.GREEN, Color.WHITE, Color.WHITE);
//        tv_item_one.setBackgroundColor(Color.RED);//被选中就为红色

    }

    private void initListener() {
        // 设置菜单栏的点击事件
        tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        tv_item_three.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new UIMultiThreadActivity.MyPagerChangeListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_item_one:
                mViewPager.setCurrentItem(0);
                dragSet(Color.GREEN, Color.WHITE, Color.WHITE);
                break;
            case R.id.tv_item_two:
                mViewPager.setCurrentItem(1);
                dragSet(Color.WHITE, Color.GREEN, Color.WHITE);
                break;
            case R.id.tv_item_three:
                mViewPager.setCurrentItem(2);
                dragSet(Color.WHITE, Color.WHITE, Color.GREEN);
                break;
        }
    }

    /**
     * 拖动设置
     * @param one
     * @param two
     * @param three
     */
    private void dragSet(int one, int two, int three) {
        tv_item_one.setBackgroundColor(one);
        tv_item_two.setBackgroundColor(two);
        tv_item_three.setBackgroundColor(three);
        //设置TextView字体加粗
        TextPaint tp1=tv_item_one.getPaint();
        TextPaint tp2=tv_item_two.getPaint();;
        TextPaint tp3=tv_item_three.getPaint();;
        if (one==Color.GREEN){
            tp1.setFakeBoldText(true);
            tp2.setFakeBoldText(false);
            tp3.setFakeBoldText(false);
        }else if(two==Color.GREEN){
            tp1.setFakeBoldText(false);
            tp2.setFakeBoldText(true);
            tp3.setFakeBoldText(false);
        }else if (three==Color.GREEN) {
            tp1.setFakeBoldText(false);
            tp2.setFakeBoldText(false);
            tp3.setFakeBoldText(true);
        }
    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    dragSet(Color.GREEN, Color.WHITE, Color.WHITE);
                    break;
                case 1:
                    dragSet(Color.WHITE, Color.GREEN, Color.WHITE);
                    break;
                case 2:
                    dragSet(Color.WHITE, Color.WHITE, Color.GREEN);
                    break;
            }
        }
    }
}
