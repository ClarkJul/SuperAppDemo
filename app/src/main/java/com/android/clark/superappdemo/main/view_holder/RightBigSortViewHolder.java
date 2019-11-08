package com.android.clark.superappdemo.main.view_holder;


import android.view.View;
import android.widget.TextView;

;import androidx.annotation.Nullable;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.bean.RightBean;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.adapter.SimpleViewHolder;


public class RightBigSortViewHolder extends SimpleViewHolder<RightBean> {

    TextView tvTitle;

    public RightBigSortViewHolder(View itemView, @Nullable SimpleRecyclerAdapter<RightBean> adapter) {
        super(itemView, adapter);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }

    @Override
    protected void refreshView(RightBean bean) {
        tvTitle.setText(bean.getData());
    }

}
