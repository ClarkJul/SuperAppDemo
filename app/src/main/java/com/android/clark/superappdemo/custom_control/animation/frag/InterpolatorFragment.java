package com.android.clark.superappdemo.custom_control.animation.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.android.clark.superappdemo.R;

public class InterpolatorFragment extends Fragment implements View.OnClickListener {
    private ImageView img;
    private Button accdecButton;
    private Button accButton;
    private Button decButton;
    private Button antButton;
    private Button oveButton;
    private Button antoveButton;
    private Button cycButton;
    private Button bouButton;
    private Button linButton;

    private ToggleButton tb;
    private Animation animation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_anim_interpolator, container, false);
        initViews(view);

        return view;

    }

    /**
     * @param view
     *
     * 设置动画为加速动画(动画播放中越来越快)
     *   android:interpolator="@android:anim/accelerate_interpolator"
     *设置动画为减速动画(动画播放中越来越慢)
     *   android:interpolator="@android:anim/decelerate_interpolator"
     *设置动画为先加速在减速(开始速度最快 逐渐减慢)
     *   android:interpolator="@android:anim/accelerate_decelerate_interpolator"
     *先反向执行一段，然后再加速反向回来（相当于我们弹簧，先反向压缩一小段，然后在加速弹出）
     *   android:interpolator="@android:anim/anticipate_interpolator"
     *同上先反向一段，然后加速反向回来，执行完毕自带回弹效果（更形象的弹簧效果）
     *   android:interpolator="@android:anim/anticipate_overshoot_interpolator"
     *执行完毕之后会回弹跳跃几段（相当于我们高空掉下一颗皮球，到地面是会跳动几下）
     *   android:interpolator="@android:anim/bounce_interpolator"
     *循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2* mCycles* Math.PI* input)
     *   android:interpolator="@android:anim/cycle_interpolator"
     *线性均匀改变
     *   android:interpolator="@android:anim/linear_interpolator"
     *加速执行，结束之后回弹
     *   android:interpolator="@android:anim/overshoot_interpolator"
     *
     */
    private void initViews(final View view) {
        img = view.findViewById(R.id.test_image);
        accdecButton = view.findViewById(R.id.btn_acc_dec);
        accButton = view.findViewById(R.id.btn_acc);
        decButton = view.findViewById(R.id.btn_dec);
        antButton = view.findViewById(R.id.btn_anticipate);
        oveButton = view.findViewById(R.id.btn_overshoot);
        antoveButton = view.findViewById(R.id.btn_anticipate_overshoot);
        cycButton = view.findViewById(R.id.btn_cycle);
        bouButton = view.findViewById(R.id.btn_bounce);
        linButton = view.findViewById(R.id.btn_linear);


        accdecButton.setOnClickListener(this);
        accButton.setOnClickListener(this);
        decButton.setOnClickListener(this);
        antButton.setOnClickListener(this);
        oveButton.setOnClickListener(this);
        antoveButton.setOnClickListener(this);
        cycButton.setOnClickListener(this);
        bouButton.setOnClickListener(this);
        linButton.setOnClickListener(this);

        if (animation==null){
            animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanim);
        }
        tb = view.findViewById(R.id.toggle);
        tb.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotateanim);
                }else {
                    animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanim);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_acc_dec:
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_acc:
                animation.setInterpolator(new AccelerateInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_anticipate:
                animation.setInterpolator(new AnticipateInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_anticipate_overshoot:
                animation.setInterpolator(new AnticipateOvershootInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_overshoot:
                animation.setInterpolator(new OvershootInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_bounce:
                animation.setInterpolator(new BounceInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_cycle:
                animation.setInterpolator(new CycleInterpolator(2));
                img.startAnimation(animation);
                break;
            case R.id.btn_dec:
                animation.setInterpolator(new DecelerateInterpolator());
                img.startAnimation(animation);
                break;
            case R.id.btn_linear:
                animation.setInterpolator(new LinearInterpolator());
                img.startAnimation(animation);
                break;
            default:
                break;
        }
    }
}
