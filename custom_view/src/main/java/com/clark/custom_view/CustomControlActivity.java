package com.clark.custom_view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CustomControlActivity extends AppCompatActivity {

    private CircularProgressButton downloadButton;

    volatile int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_control);

        downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnProgressListener(new CircularProgressButton.OnProgressListener() {
            @Override
            public void onProgressFinish() {
                Log.d("CustomControlActivity", "onProgressFinish=> 下载完成");
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadButton.isInProgress()){
                    downloadButton.doPauseProgress();
                }else {
                    startDownload(downloadButton);
                }
            }
        });
    }

    private void startDownload(CircularProgressButton button){
        button.doStartProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progress<=100){
                        Thread.sleep(200);
                        if (progress>=100){
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    button.doFinishProgress();
                                }
                            });
                        }else {
                            button.setProgress(progress,100);
                        }
                        progress++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    progress=0;
                }
            }
        }).start();
    }

}
