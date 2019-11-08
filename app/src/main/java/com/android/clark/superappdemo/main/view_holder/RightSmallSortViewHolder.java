package com.android.clark.superappdemo.main.view_holder;


import android.view.View;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.bean.RightBean;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.adapter.SimpleViewHolder;


public class RightSmallSortViewHolder extends SimpleViewHolder<RightBean> {

    private TextView textView;

    public RightSmallSortViewHolder(View itemView, SimpleRecyclerAdapter<RightBean> adapter) {
        super(itemView, adapter);
        textView = itemView.findViewById(R.id.tv_small);
    }

    @Override
    protected void refreshView(RightBean data) {
        textView.setText(data.getData());
    }
}
