package com.android.clark.superappdemo.custom_control.draw_picture.frag;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.clark.superappdemo.R;
import com.android.clark.superappdemo.custom_control.draw_picture.FirstCustomView;
import com.android.clark.superappdemo.custom_control.draw_picture.SecondCustomView;
import com.android.clark.superappdemo.custom_control.draw_picture.ThirdCustomView;

public class BaseDrawFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_draw_pic_base,container,false);
        addCustomView(view);
        return view;
    }

    private void addCustomView(View view) {
        RelativeLayout relativeLayout=view.findViewById(R.id.root_frag);
        FirstCustomView mCustomView=new FirstCustomView(getContext());
//        frameLayout.addView(mCustomView);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(400,400);
        params.leftMargin=20;
        params.topMargin=20;
        relativeLayout.addView(mCustomView,params);

        SecondCustomView secondCustomView=new SecondCustomView(getContext());
        RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(400,400);
        params1.topMargin=20;
        params1.leftMargin=450;
        relativeLayout.addView(secondCustomView,params1);

        ThirdCustomView thirdCustomView=new ThirdCustomView(getContext());
        RelativeLayout.LayoutParams params2=new RelativeLayout.LayoutParams(1000,400);
        params2.topMargin=440;
        params2.leftMargin=20;
        relativeLayout.addView(thirdCustomView,params2);
    }

}
