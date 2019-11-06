package com.clark.download.appdownload.view;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.clark.download.R;
import com.clark.download.appdownload.AppInfo;
import com.clark.download.appdownload.Config;
import com.clark.download.appdownload.content_provider.ProviderManager;
import com.clark.download.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadedActivity extends AppCompatActivity {
    private static final String TAG = "DownloadedActivity";

    private RecyclerView recyclerView;
    private MultiRecyAdapter adapter;
    private List<AppInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded);

        initDatas();
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.lv_downloaded);
        TextView isNoData = findViewById(R.id.tv_no_data);
        isNoData.setVisibility(View.GONE);

        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            isNoData.setVisibility(View.VISIBLE);
        } else {
            adapter = new MultiRecyAdapter(list, this);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initDatas() {

        list=new ArrayList<>();

        //从本地解析json数据
        String appInfoStr = Utils.getJson(this, "HomeListAppInfo.json");
        List<AppInfo> appInfos = new ArrayList<>();
        appInfos = new Gson().fromJson(appInfoStr, new TypeToken<List<AppInfo>>() {
        }.getType());

        //从下载文件夹中获取已下载或下载未完成的文件
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        final List<File> fileList = Utils.getFilesAllName(path);
        Log.i("------->", "fileList.size=" + fileList.size());

        if (!fileList.isEmpty() && fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                for (int j = 0; j < appInfos.size(); j++) {
                    AppInfo app = appInfos.get(j);
                    if (file.getName().equals(app.name + ".apk")) {
                        //设置appInfo的state
                        if (file.length() == ProviderManager.getInstance(this).query(app.id).size) {
                            appInfos.get(j).currentState = Config.DOWNLOAD_STATE_DOWNLOADED;
                            list.add(appInfos.get(j));
                        }
                    }
                }
            }
        }
        Log.i(TAG,"list.size="+list.size());
    }
}
