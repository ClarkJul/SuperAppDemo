package com.android.clark.superappdemo.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.bean.LeftBean;
import com.android.clark.superappdemo.main.view_holder.LeftViewHolder;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.adapter.SimpleViewHolder;


public class LeftAdapter extends SimpleRecyclerAdapter<LeftBean> {

    private int mSelectedPosition;

    @Override
    public SimpleViewHolder<LeftBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_search_sort_left, parent, false), this);
    }

    public void setSelectedPosition(int position) {
        mListData.get(mSelectedPosition).setSelect(false);
        notifyItemChanged(mSelectedPosition);
        mListData.get(position).setSelect(true);
        notifyItemChanged(position);
        mSelectedPosition = position;
    }
}
