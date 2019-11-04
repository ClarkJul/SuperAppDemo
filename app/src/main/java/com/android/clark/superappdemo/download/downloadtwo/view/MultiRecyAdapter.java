package com.android.clark.superappdemo.download.downloadtwo.view;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.download.downloadtwo.AppInfo;
import com.android.clark.superappdemo.download.downloadtwo.Config;
import com.android.clark.superappdemo.download.downloadtwo.down.MultiDownloadManager;
import com.bumptech.glide.Glide;

import java.util.List;

public class MultiRecyAdapter extends RecyclerView.Adapter<MultiRecyAdapter.MyViewHolder> {
    public static final String TAG="MultiRecyAdapter";
    public List<AppInfo> mDatas;
    private DownloadButtonClickListener mListener;

    private Context mContext;
    private LayoutInflater inflater;

    public MultiRecyAdapter(List<AppInfo> mDatas, DownloadButtonClickListener mListener, Context mContext) {
        this.mDatas = mDatas;
        this.mListener = mListener;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }
    public MultiRecyAdapter(List<AppInfo> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.download_recycle_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        final AppInfo appInfo = mDatas.get(position);
        //初始化
        Glide.with(mContext).load(appInfo.icon).into(myViewHolder.appIcon);//注意URL的格式是否正确
        myViewHolder.appName.setText(appInfo.name);
        myViewHolder.appContent.setText(appInfo.downloads + "");
        myViewHolder.appDescription.setText(appInfo.sdesc);
        myViewHolder.installBar.setVisibility(View.GONE);
        myViewHolder.installBar.setProgress(0);

        updateState(myViewHolder.installBar,appInfo.currentState,appInfo.progress,myViewHolder.installButton);

        myViewHolder.installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"App："+appInfo.toString());
                if (appInfo.currentState == Config.DOWNLOAD_STATE_START
                        || appInfo.currentState == Config.DOWNLOAD_STATE_PAUSED
                        || appInfo.currentState == Config.DOWNLOAD_STATE_ERROR) {
                    mListener.onStartClick(appInfo);
                } else if (appInfo.currentState == Config.DOWNLOAD_STATE_DOWNLOADING) {
                    mListener.onPauseClick(appInfo);
                } else if (appInfo.currentState == Config.DOWNLOAD_STATE_DOWNLOADED) {
                    //开始安装
                    MultiDownloadManager.getInstance().install(appInfo,mContext);
                }
            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position);
        }else {
            final AppInfo appInfo = mDatas.get(position);
            updateState(holder.installBar,appInfo.currentState,appInfo.progress,holder.installButton);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appContent;
        TextView appDescription;
        Button installButton;
        ProgressBar installBar;

        MyViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.home_app_list_item_app_icon);
            appName = itemView.findViewById(R.id.home_app_list_item_app_name);
            appContent = itemView.findViewById(R.id.home_app_list_item_app_info);
            appDescription = itemView.findViewById(R.id.home_app_list_item_app_description);
            installButton = itemView.findViewById(R.id.home_app_list_item_app_install_button);
            installBar = itemView.findViewById(R.id.app_item_progress);
        }
    }
    public void updateState(ProgressBar progressBar,int state, int progress, Button button){
        if (state == Config.DOWNLOAD_STATE_START) {
            button.setText("开始");
            button.setTextColor(mContext.getResources().getColor(R.color.lightgreen));
        } else if (state == Config.DOWNLOAD_STATE_WAITING) {
            button.setText("等待");
            button.setTextColor(mContext.getResources().getColor(R.color.orange));
            progressBar.setVisibility(View.GONE);
        } else if (state == Config.DOWNLOAD_STATE_DOWNLOADING) {
            button.setText(progress+"%");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress);
            button.setTextColor(Color.BLACK);
        } else if (state == Config.DOWNLOAD_STATE_PAUSED) {
            button.setText("继续");
            button.setTextColor(Color.BLACK);
            progressBar.setVisibility(View.GONE);
        } else if (state == Config.DOWNLOAD_STATE_DOWNLOADED) {
            button.setText("安装");
            button.setTextColor(mContext.getResources().getColor(R.color.black));
            progressBar.setVisibility(View.GONE);
        } else if (state == Config.DOWNLOAD_STATE_ERROR) {
            button.setText("错误");
            button.setTextColor(Color.BLACK);
            progressBar.setVisibility(View.GONE);
        }
        if (progress>=100){
            progressBar.setVisibility(View.GONE);
        }
    }
}
