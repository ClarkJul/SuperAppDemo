package com.android.clark.superappdemo.experiment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

import java.util.List;


public class AdaAdapter extends RecyclerView.Adapter<AdaAdapter.ViewHolder> {
    private static final String TAG="AdaAdapter";

    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mInflater;

    public AdaAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.i(TAG,"执行了onCreateViewHolder");
        View view = mInflater.inflate(R.layout.recycler_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.i(TAG,"执行了onBindViewHolder");
        String text = mDatas.get(position);
        viewHolder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,"执行了getItemCount");
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.file_name);
        }
    }
}
