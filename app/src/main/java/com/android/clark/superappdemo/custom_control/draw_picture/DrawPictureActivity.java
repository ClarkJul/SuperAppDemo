package com.android.clark.superappdemo.custom_control.draw_picture;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.custom_control.draw_picture.frag.BaseDrawFragment;
import com.android.clark.superappdemo.custom_control.draw_picture.frag.SecondDrawFragment;
import com.android.clark.superappdemo.custom_control.draw_picture.frag.ThirdDrawFragment;

import java.util.ArrayList;
import java.util.List;

public class DrawPictureActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager drawPicViewPager;
    private List<Fragment> fragments;

    private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_picture);

        initViews();
        initDatas();
        initListener();
    }

    private void initListener() {
        // 设置菜单栏的点击事件
        tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        tv_item_three.setOnClickListener(this);

        drawPicViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int item) {
                switch (item) {
                    case 0:
                        tv_item_one.setBackgroundColor(Color.RED);
                        tv_item_two.setBackgroundColor(Color.WHITE);
                        tv_item_three.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        tv_item_one.setBackgroundColor(Color.WHITE);
                        tv_item_two.setBackgroundColor(Color.RED);
                        tv_item_three.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        tv_item_one.setBackgroundColor(Color.WHITE);
                        tv_item_two.setBackgroundColor(Color.WHITE);
                        tv_item_three.setBackgroundColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initDatas() {
        fragments = new ArrayList<>();
        fragments.add(new BaseDrawFragment());
        fragments.add(new SecondDrawFragment());
        fragments.add(new ThirdDrawFragment());

        DrawViewPagerAdapter adapter = new DrawViewPagerAdapter(getSupportFragmentManager());
        drawPicViewPager.setAdapter(adapter);
        drawPicViewPager.setCurrentItem(0);
        tv_item_one.setBackgroundColor(Color.RED);//被选中就为红色
    }

    private void initViews() {
        drawPicViewPager = findViewById(R.id.vp_draw_pic);

        tv_item_one = findViewById(R.id.tv_item_one);
        tv_item_two = findViewById(R.id.tv_item_two);
        tv_item_three = findViewById(R.id.tv_item_three);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_item_one:
                drawPicViewPager.setCurrentItem(0);
                tv_item_one.setBackgroundColor(Color.RED);
                tv_item_two.setBackgroundColor(Color.WHITE);
                tv_item_three.setBackgroundColor(Color.WHITE);
                break;
            case R.id.tv_item_two:
                drawPicViewPager.setCurrentItem(1);
                tv_item_one.setBackgroundColor(Color.WHITE);
                tv_item_two.setBackgroundColor(Color.RED);
                tv_item_three.setBackgroundColor(Color.WHITE);
                break;
            case R.id.tv_item_three:
                drawPicViewPager.setCurrentItem(2);
                tv_item_one.setBackgroundColor(Color.WHITE);
                tv_item_two.setBackgroundColor(Color.WHITE);
                tv_item_three.setBackgroundColor(Color.RED);
                break;
        }
    }

    class DrawViewPagerAdapter extends FragmentPagerAdapter {

        public DrawViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
