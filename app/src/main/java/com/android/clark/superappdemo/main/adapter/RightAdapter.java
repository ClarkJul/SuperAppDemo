package com.android.clark.superappdemo.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.bean.RightBean;
import com.android.clark.superappdemo.main.view_holder.RightBigSortViewHolder;
import com.android.clark.superappdemo.main.view_holder.RightSmallSortViewHolder;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.adapter.SimpleViewHolder;


public class RightAdapter extends SimpleRecyclerAdapter<RightBean> {


    @Override
    public SimpleViewHolder<RightBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new RightBigSortViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item_right_big_sort, parent, false), this);
        } else {
            return new RightSmallSortViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item_right_small_sort, parent, false), this);
        }
    }

    @Override
        public int getItemViewType(int position) {
        return mListData.get(position).getType();
    }
}
