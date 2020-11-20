package com.android.clark.superappdemo.ui_thread_multithread;

import android.util.Log;
import android.view.View;

import com.android.clark.superappdemo.R;
import com.clark.common.base.act.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends BaseActivity {
    private static final String TAG=ThreadPoolActivity.class.getSimpleName();


    private ThreadPoolExecutor threadPoolExecutor;
    private ExecutorService fixedThreadPool;
    private ExecutorService cachedThreadPool;
    private ExecutorService singleThreadPool;
    private ExecutorService scheduledThreadPool;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_thread_pool;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        //创建核心线程数为2.最大线程数为5，阻塞队列容量为2的标准线程池
        threadPoolExecutor=new ThreadPoolExecutor(2,5,3, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2));

        //创建固定线程数为3的FixedThreadPool，核心线程数和最大线程数相等，阻塞队列LinkedBlockingQueue容量Integer.MAX_VALUE
        fixedThreadPool= Executors.newFixedThreadPool(3);


        //核心线程数为0，,最大线程数Integer.MAX_VALUE，阻塞队列SynchronousQueue ===> TransferStack先进先出？
        cachedThreadPool=Executors.newCachedThreadPool();

        //核心线程数为1，,最大线程数1，阻塞队列LinkedBlockingQueue容量Integer.MAX_VALUE
        singleThreadPool=Executors.newSingleThreadExecutor();

        //核心线程数为2，,最大线程数Integer.MAX_VALUE，阻塞队列DelayedWorkQueue容量默认16
        scheduledThreadPool=Executors.newScheduledThreadPool(2);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    public void testThreadPoolExecutor(View view) {
        for (int i=1;i<8;i++){
            threadPoolExecutor.execute(new TestRunnable("第"+i+"次执行"));
        }
    }

    public void testFixedThreadPool(View view) {
        for (int i=1;i<10;i++){
            fixedThreadPool.execute(new TestRunnable("第"+i+"次执行"));
        }
    }

    public void testCachedThreadPool(View view) {
        for (int i=1;i<10;i++){
            cachedThreadPool.execute(new TestRunnable("第"+i+"次执行"));
        }
    }

    public void testSingleThreadExecutor(View view) {
        for (int i=1;i<10;i++){
            singleThreadPool.execute(new TestRunnable("第"+i+"次执行"));
        }
    }

    public void testScheduledThreadPool(View view) {
        for (int i=1;i<10;i++){
            scheduledThreadPool.execute(new TestRunnable("第"+i+"次执行"));
        }
    }

    class TestRunnable implements Runnable {

        private String name;
        public TestRunnable(String name) {
            this.name=name;
        }

        @Override
        public void run() {
            Log.e(TAG, "TestRunnable:name="+name+" ,Thread=" +Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
