package com.clark.custom_view.draw_picture.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.clark.custom_view.R;
import com.clark.custom_view.draw_picture.CustomEditView;
import com.clark.custom_view.draw_picture.CustomWaterView;


public class SecondDrawFragment extends Fragment {
    private CustomEditView customEditView;
    private CustomWaterView customWaterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_draw_second, container, false);

        initViews(view);
        initListener(view);
        customWaterView.startAnim();
        return view;
    }

    private void initListener(View view) {
        view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customEditView.reset();
            }
        });
    }

    private void initViews(View view) {
        customEditView = view.findViewById(R.id.custom_edit_view);
        customWaterView = view.findViewById(R.id.custom_water_view);
    }
}
