package com.android.clark.superappdemo.ui_thread_multithread.frag;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.clark.common.base.frag.BaseFragment;

import java.util.Date;

public class AsyncTaskFragment extends BaseFragment {

    // 线程变量
    MyTask mTask;
    // 主布局中的UI组件
    Button button,cancel,pause; // 加载、取消按钮
    TextView text; // 更新的UI组件
    ProgressBar progressBar; // 进度条
    private Button btn2;

    boolean started;
    @Override
    protected int getLayoutResId() {
        return R.layout.frag_thread_three;
    }

    @Override
    protected void initView(View rootView) {
        button = (Button) rootView.findViewById(R.id.button);
        pause = (Button) rootView.findViewById(R.id.pause);
        cancel = (Button) rootView.findViewById(R.id.cancel);
        text = (TextView) rootView.findViewById(R.id.text);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        btn2 = rootView.findViewById(R.id.btn_async2);
    }

    @Override
    protected void initData() {
        mTask = new MyTask();
    }

    @Override
    protected void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                if (mTask.getStatus()== AsyncTask.Status.PENDING)
                    mTask.execute();
//                mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                new MyTask().execute();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setPause(true);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started){
                    new CustomAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1");
                    new CustomAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"2");
                    new CustomAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"3");
                    new CustomAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"4");

/*                    new CustomAsyncTask().execute("1");
                    new CustomAsyncTask().execute("2");
                    new CustomAsyncTask().execute("3");
                    new CustomAsyncTask().execute("4");*/
                }
            }
        });
    }

    class MyTask extends AsyncTask<Integer, Integer, Integer>{
        private boolean isPause;
        private int currentCount=0;
        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            text.setText("加载中");
            // 执行前显示提示
        }


        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                int count = 0;
                int length = 1;
                while (count<99) {
                    if (isPause){
                        Log.d("ThreeFragment", "doInBackground: "+currentCount);
                        return 99;
                    }
                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    currentCount=count;
                    publishProgress(currentCount);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            return -1;
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.e("MyTask", "onProgressUpdate: progresses="+progresses);
            progressBar.setProgress(progresses[0]);
            text.setText("loading..." + progresses[0] + "%");

        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(Integer result) {
            Log.e("MyTask", "onPostExecute: result="+result);
            if (result==99){
                text.setText("暂停");
                return;
            }
            // 执行完毕后，则更新UI
            text.setText("加载完毕");
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {

            text.setText("已取消");
            progressBar.setProgress(0);

        }

        public void setPause(boolean pause) {
            isPause = pause;
        }
    }

    class CustomAsyncTask extends AsyncTask<String ,Void,String>{

        @Override
        protected String doInBackground(String... voids) {
            try {
                Thread.sleep(3000);
                return voids[0]+","+new Date().toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "null";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("CustomAsyncTask", "onPostExecute: "+s);
        }
    }

}
