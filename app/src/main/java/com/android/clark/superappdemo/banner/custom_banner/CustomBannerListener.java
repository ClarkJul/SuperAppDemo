package com.android.clark.superappdemo.banner.custom_banner;

import android.content.Context;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.clark.superappdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CustomBannerListener implements OnPageChangeListener {

    private Context mContext;
    private LinearLayout mLinearLayout;         //底部圆点布局
    private int mSize;                          //圆点数量
    private List<ImageView> mDotView;           //圆点容器
    private float mDistance;
    private View mView;
    RelativeLayout.LayoutParams mViewParams;

    public CustomBannerListener(Context context, LinearLayout linearLayout, View view, int size) {
        mContext = context;
        mLinearLayout = linearLayout;
        mSize = size;
        mView = view;
        initData();
    }

    private void initData() {
        mDotView = new ArrayList<>();
        for (int i = 0; i < mSize; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setBackgroundResource(R.drawable.banner_custom_dot_selector);
            if (i != 0) {
                params.leftMargin = 3;     //设置圆点相距距离
            }
            mLinearLayout.addView(imageView, params);
            mDotView.add(imageView);
        }
        //以下为修改的主要代码
        mViewParams = (RelativeLayout.LayoutParams) mView.getLayoutParams();
        mView.post(new Runnable() {     //获得两个点之间的距离
            @Override
            public void run() {
                mDistance = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int i1) {
        position = position % mSize;
        float leftMargin = 0;
        if (position != mSize - 1) {                                    //如果是在最后一页, 往右滑就不增加滑动距离
            leftMargin = mDistance * (position + positionOffset);
        } else {
            leftMargin = mDistance * position;
        }
        if(mViewParams!=null){
            mViewParams.leftMargin = Math.round(leftMargin);
            mView.setLayoutParams(mViewParams);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
