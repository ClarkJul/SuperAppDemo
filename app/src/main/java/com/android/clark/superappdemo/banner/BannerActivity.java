package com.android.clark.superappdemo.banner;

import android.app.Activity;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.banner.custom_banner.CustomBannerAdapter;
import com.android.clark.superappdemo.banner.custom_banner.CustomBannerListener;
import com.android.clark.superappdemo.banner.custom_banner.CustomBannerViewPager;
import com.android.clark.superappdemo.banner.custom_banner.DefaultTransformer;
import com.android.clark.superappdemo.banner.custom_banner.SecondTransformer;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannerActivity extends Activity implements ViewPager.OnPageChangeListener {
    private Banner bannerFirst;
    private Uri[] images = {
            Uri.parse("http://static.zookingsoft.com/appstore/files/201709/2017092557545548.jpg"),
            Uri.parse("http://static.zookingsoft.com/appstore/files/201709/2017092653555610.jpg"),
            Uri.parse("http://static.zookingsoft.com/appstore/files/201709/2017091899571021.jpg"),
            Uri.parse("http://static.zookingsoft.com/appstore/files/201709/2017092551541021.jpg"),
            Uri.parse("http://static.zookingsoft.com/appstore/files/201709/2017092210153995.jpg")};

    private RelativeLayout bannerParent;
    private CustomBannerViewPager customBannerViewPager;
    private LinearLayout mLinearLayout;
    List<Uri> bannerImages;
    private int prePosition = 0;
    private PullThread pullThread = new PullThread();
    private boolean isAutoPlay = true;

    private HandlerThread mHandlerThread;
    private Handler mHandler;
    //UI线程的Handler
    private Handler mainHandler = new Handler();

    //以防退出界面后Handler还在执行
    private boolean isUpdateInfo;
    //用以表示该handler的常数
    private static final int MSG_UPDATE_INFO = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerFirst.start();
        isUpdateInfo = true;
        mHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    private void initViews() {
        bannerFirst = findViewById(R.id.banner_first);

        //设置图片加载器
        bannerFirst.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerFirst.setImages(Arrays.asList(images));
        //设置是否可以手动滑动
        bannerFirst.setViewPagerIsScroll(false);
        //设置自动轮播，默认为true
        bannerFirst.isAutoPlay(true);
        //设置轮播时间
        bannerFirst.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        bannerFirst.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
//        bannerFirst.start();

        mLinearLayout = findViewById(R.id.custom_dot_group);
        customBannerViewPager = findViewById(R.id.custom_pager);
        bannerParent = findViewById(R.id.rl_banner_parent);
    }

    private void initData() {
        bannerImages = Arrays.asList(images);
        if (bannerImages.isEmpty()) {
            bannerParent.setVisibility(View.GONE);
        } else {
            customBannerViewPager.setAdapter(new CustomBannerAdapter(bannerImages, this));
            //禁止手动滑动
            customBannerViewPager.setScanScroll(false);
/*        customBannerViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/
            if (bannerImages.size() == 1) {
                mLinearLayout.setVisibility(View.GONE);
            } else {
                mLinearLayout.removeAllViews();
                //有多少张图就放置几个点
                for (int i = 0; i < bannerImages.size(); i++) {
                    View v = new View(this);
                    v.setBackgroundResource(R.drawable.white_radius);
                   //将dp转换成px
                    int dotWidth=DensityUtil.dip2px(this,3);
                    int dotHeight=DensityUtil.dip2px(this,3);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotWidth, dotHeight);
                    layoutParams.leftMargin = dotWidth;
                    mLinearLayout.addView(v, layoutParams);
                }
                customBannerViewPager.setOnPageChangeListener(this);
                //设置切换页面的动画效果
                customBannerViewPager.setPageTransformer(true, new DefaultTransformer());
//        customBannerViewPager.setPageTransformer(true, new SecondTransformer());
                customBannerViewPager.setCurrentItem(0);  //这个是无线轮询的关键，设置当前的item为1000
                mLinearLayout.getChildAt(0).setBackgroundResource(R.drawable.blue_radius);
                prePosition = 0;

                //开启轮播
//                isAutoPlay = true;
//                if (pullThread.isInterrupted()){
//                    pullThread.notify();
//                }
                initThread();

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        bannerFirst.stopAutoPlay();
        isUpdateInfo = false;
        mHandler.removeMessages(MSG_UPDATE_INFO);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerFirst.stopAutoPlay();
        isAutoPlay = false;
        pullThread.interrupt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % bannerImages.size();
        mLinearLayout.getChildAt(newPosition).setBackgroundResource(R.drawable.blue_radius);
        mLinearLayout.getChildAt(prePosition).setBackgroundResource(R.drawable.white_radius);
        prePosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

    private void initThread() {
        mHandlerThread = new HandlerThread("handlerThread-test");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            public void handleMessage(Message msg) {
                try {
                    Thread.sleep(2000);
                    //子线程不能更新UI，创建Handler更新UI
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            customBannerViewPager.setCurrentItem(customBannerViewPager.getCurrentItem() + 1);
                        }
                    });
                    if (isUpdateInfo) {
                        mHandler.sendEmptyMessage(MSG_UPDATE_INFO);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    class PullThread extends Thread {
        @Override
        public void run() {
            while (isAutoPlay) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customBannerViewPager.setCurrentItem(customBannerViewPager.getCurrentItem() + 1);
                    }
                });
            }
        }
    }
}

