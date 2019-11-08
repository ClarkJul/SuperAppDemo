package com.android.clark.superappdemo.main.view_holder;


import android.view.View;
import android.widget.TextView;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.main.bean.LeftBean;
import com.clark.common.adapter.SimpleRecyclerAdapter;
import com.clark.common.adapter.SimpleViewHolder;


public class LeftViewHolder extends SimpleViewHolder<LeftBean> {

    /**
     * tvName显示大类名称，view是显示被选中的黄色标记
     */
    private TextView tvName;
    private View view;

    public LeftViewHolder(View itemView,  SimpleRecyclerAdapter<LeftBean> adapter) {
        super(itemView, adapter);
        tvName = (TextView) itemView.findViewById(R.id.tv_left);
        view = itemView.findViewById(R.id.view);
    }

    @Override
    protected void refreshView(LeftBean bean) {
       tvName.setText(bean.getData());
        //item点击后背景的变化
        if (bean.isSelect()) {
            view.setVisibility(View.VISIBLE);
            tvName.setBackgroundResource(R.color.yellow);
            tvName.setTextColor(getContext().getResources().getColor(R.color.green));
        } else {
            view.setVisibility(View.GONE);
            tvName.setBackgroundResource(R.color.white);
            tvName.setTextColor(getContext().getResources().getColor(R.color.text_color_gray));
        }
    }
}
