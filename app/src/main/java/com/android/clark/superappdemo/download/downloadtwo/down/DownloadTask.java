package com.android.clark.superappdemo.download.downloadtwo.down;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.android.clark.superappdemo.SupperApplication;
import com.android.clark.superappdemo.download.downloadtwo.content_provider.ProviderBean;
import com.android.clark.superappdemo.download.downloadtwo.content_provider.ProviderManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.Provider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<Void, Integer, Integer> {
    private static final String TAG = "DownloadTask";

    private static final int STATUS_SUCCEED = 1;
    private static final int STATUS_PAUSED = 2;
    private static final int STATUS_CANCELED = 3;
    private static final int STATUS_FAILED = 4;

    private boolean isPause = false;
    private boolean isCancel = false;

    private DownLoadListener mListener;
    private DownloadInfo downloadInfo;

    public DownloadTask(DownloadInfo downloadInfo, DownLoadListener listener) {
        this.downloadInfo = downloadInfo;
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String url = downloadInfo.downloadUrl;
        String name = downloadInfo.appName;
        Log.i(TAG, "下载的app的名称："+name);
        long downloadedLength = 0;
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File(directory+"/" + name+".apk");
        Log.i("--------->","下载路径："+file.getPath());
        //判断任务是开始还是取消
        if (isCancel) {
            if (file.exists()) {
                file.delete();
            }
            return STATUS_CANCELED;
        }

        if (file.exists()) {
            downloadedLength = file.length();
        }

        long contentLength = getContentLength(url);//获取下载文件的大小
        //保存文件信息到contentProvider
        if (contentLength!=0){
            ProviderBean bean=new ProviderBean();
            bean.id=downloadInfo.appId;
            bean.name=downloadInfo.appName;
            bean.size=contentLength;
            ProviderManager.getInstance(SupperApplication.getContext()).insertData(bean);
        }
        //无法下载
        if (contentLength == 0) {
            return STATUS_FAILED;
        } else if (contentLength == downloadedLength) {
            return STATUS_SUCCEED;
        }
        //开始下载
        isPause = false;
        isCancel = false;

        InputStream is = null;
        RandomAccessFile saveFile = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                addHeader("RANGE", "bytes=" + downloadedLength + "-") //指定从哪一个字节下载
                .url(url).build();
        try {
            Response response = client.newCall(request).execute();
            //写入到本地
            if (response != null) {
                Log.d(TAG, "doInBackground: response not null");
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadedLength);
                int len;
                byte[] buffer = new byte[1024];
                int temp=-1;
                while ((len = is.read(buffer)) != -1) {
                    if (isPause) {
                        return STATUS_PAUSED;
                    } else if (isCancel) {
                        if (file.exists()) {
                            file.delete();
                        }
                        return STATUS_CANCELED;
                    }
                    //获取已下载的进度
                    saveFile.write(buffer, 0, len);
                    downloadedLength += len;
                    int progress = (int) (downloadedLength * 100 / contentLength);
                    if (temp!=progress){
                        publishProgress(progress);
                        temp=progress;
                    }
                }
                response.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (contentLength == downloadedLength) {
            return STATUS_SUCCEED;
        }
        return STATUS_FAILED;
    }

    public static long getContentLength(String url) {
        long contentLength = 0;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            contentLength = response.body().contentLength();
            response.close();
        } else {
            Log.d(TAG, "getContentLength: response null");
        }
        return contentLength;
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer) {
            case STATUS_FAILED:
                mListener.onFail(downloadInfo);
                break;
            case STATUS_PAUSED:
                mListener.onDownloadPause(downloadInfo);
                break;
            case STATUS_CANCELED:
                mListener.onCancel(downloadInfo);
            case STATUS_SUCCEED:
                mListener.onFinish(downloadInfo);
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mListener.updateProgress(downloadInfo, values[0]);
    }

    public void setPause() {
        isPause = true;
    }

    public void setCancel() {
        isCancel = true;
    }

}
