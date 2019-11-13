package com.clark.download.appdownload.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clark.download.R;
import com.clark.download.appdownload.AppInfo;
import com.clark.download.appdownload.Config;
import com.clark.download.appdownload.down.MultiDownloadManager;
import com.clark.download.custom.DownloadProgressButton;

import java.util.List;

public class MultiRecyAdapterNew extends RecyclerView.Adapter<MultiRecyAdapterNew.MyViewHolder> {
    public static final String TAG="MultiRecyAdapterNew";
    public List<AppInfo> mDatas;
    private DownloadButtonClickListener mListener;

    private Context mContext;
    private LayoutInflater inflater;

    public MultiRecyAdapterNew(List<AppInfo> mDatas, DownloadButtonClickListener mListener, Context mContext) {
        this.mDatas = mDatas;
        this.mListener = mListener;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }
    public MultiRecyAdapterNew(List<AppInfo> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.download_recycle_item_new, viewGroup, false);
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

        updateState(myViewHolder.installButton,appInfo.currentState,appInfo.progress);

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
            updateState(holder.installButton,appInfo.currentState,appInfo.progress);
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
        DownloadProgressButton installButton;

        MyViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.home_app_list_item_app_icon);
            appName = itemView.findViewById(R.id.home_app_list_item_app_name);
            appContent = itemView.findViewById(R.id.home_app_list_item_app_info);
            appDescription = itemView.findViewById(R.id.home_app_list_item_app_description);
            installButton = itemView.findViewById(R.id.app_item_progress);
        }
    }
    private void updateState(DownloadProgressButton button,int state, int progress){
        if (state == Config.DOWNLOAD_STATE_START) {
            button.setTipsMessage("安装");
        } else if (state == Config.DOWNLOAD_STATE_WAITING) {
            button.doStartProgress();
            button.setProgress(progress,100);
        } else if (state == Config.DOWNLOAD_STATE_DOWNLOADING) {
            button.setProgress(progress,100);
        } else if (state == Config.DOWNLOAD_STATE_PAUSED) {
            if (button.isInProgress()){
                button.setTipsMessage("继续");
                button.doPauseProgress();
            }
        } else if (state == Config.DOWNLOAD_STATE_DOWNLOADED) {
            button.setTipsMessage("安装中");
            button.doFinishProgress();
        } else if (state == Config.DOWNLOAD_STATE_ERROR) {

        }
    }
}
