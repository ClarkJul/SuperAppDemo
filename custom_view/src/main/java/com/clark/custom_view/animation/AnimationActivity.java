package com.clark.custom_view.animation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.clark.custom_view.R;
import com.clark.custom_view.animation.frag.InterpolatorFragment;
import com.clark.custom_view.animation.frag.ThreeFragment;
import com.clark.custom_view.animation.frag.frag_two.ValueAnimatorFragment;

import java.util.ArrayList;
import java.util.List;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> fragments;


    private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initViews();
        initListener();
        initDatas();
    }

    private void initViews() {
        mViewPager = findViewById(R.id.animation_view_pager);

        tv_item_one = findViewById(R.id.tv_item_one);
        tv_item_two = findViewById(R.id.tv_item_two);
        tv_item_three = findViewById(R.id.tv_item_three);

    }

    private void initDatas() {
        fragments = new ArrayList<>();
        InterpolatorFragment fragmentOne = new InterpolatorFragment();

        fragments.add(fragmentOne);
        fragments.add(new ValueAnimatorFragment());
        fragments.add(new ThreeFragment());

        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);  //初始化显示第一个页面
        tv_item_one.setBackgroundColor(Color.RED);//被选中就为红色

    }

    private void initListener() {
        // 设置菜单栏的点击事件
        tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        tv_item_three.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_item_one) {
            mViewPager.setCurrentItem(0);
            tv_item_one.setBackgroundColor(Color.RED);
            tv_item_two.setBackgroundColor(Color.WHITE);
            tv_item_three.setBackgroundColor(Color.WHITE);
        } else if (id == R.id.tv_item_two) {
            mViewPager.setCurrentItem(1);
            tv_item_one.setBackgroundColor(Color.WHITE);
            tv_item_two.setBackgroundColor(Color.RED);
            tv_item_three.setBackgroundColor(Color.WHITE);
        } else if (id == R.id.tv_item_three) {
            mViewPager.setCurrentItem(2);
            tv_item_one.setBackgroundColor(Color.WHITE);
            tv_item_two.setBackgroundColor(Color.WHITE);
            tv_item_three.setBackgroundColor(Color.RED);
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
    }

}
