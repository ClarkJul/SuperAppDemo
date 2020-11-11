package com.android.clark.superappdemo.custom_control.animation.frag.frag_two;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

public class ValueAnimatorFragment extends Fragment implements View.OnClickListener {
    private static final int ANIM_TYPE_LOC = 1;
    private static final int ANIM_TYPE_COL = 2;
    private static final int ANIM_TYPE_CHA = 3;
    private static final int ANIM_TYPE_POINT = 4;
    private int animType = ANIM_TYPE_LOC;//默认为位置动画

    private TextView test;
    private Button startButton;
    private Button cancelButton;
    private Button locationButton;
    private Button colorButton;
    private Button charButton;
    private Button pointButton;
    private MyPointView myPointView;
    private ValueAnimator repeatAnimator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_anim_value_animator, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        test = view.findViewById(R.id.text_test);
        startButton = view.findViewById(R.id.btn_start_anim);
        cancelButton = view.findViewById(R.id.btn_cancel_anim);
        locationButton = view.findViewById(R.id.btn_location_anim);
        colorButton = view.findViewById(R.id.btn_color_anim);
        charButton = view.findViewById(R.id.btn_char_anim);
        pointButton = view.findViewById(R.id.btn_point_anim);

        myPointView = view.findViewById(R.id.point_view);

        startButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        locationButton.setOnClickListener(this);
        colorButton.setOnClickListener(this);
        charButton.setOnClickListener(this);
        pointButton.setOnClickListener(this);

    }

    private ValueAnimator selectAnim() {
        if (animType == ANIM_TYPE_LOC) {
            return doLocationAnim();
        } else if (animType == ANIM_TYPE_COL) {
            return doColorAnim();
        } else if (animType == ANIM_TYPE_CHA) {
            return doCharAnim();
        } else if (animType == ANIM_TYPE_POINT) {
            return doPointAnim();
        }
        return null;
    }

    /**
     * 改变位置的动画
     */
    private ValueAnimator doLocationAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 400); //定义动画为ofInt,也可以定义ValueAnimator.ofFloat()
        animator.setDuration(1000);//设置动画的时间

/*        //获取控件在窗口的位置
        int[] location = new int[2] ;
//        test.getLocationInWindow(location);
        test.getLocationOnScreen(location);
        final int startX=location[0];
        final int startY=location[1];
        Log.i("---------->","startX:"+startX+"  startY:"+startY);*/

        //设置动画更新是的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                //layout(int l, int t, int r, int b),relative to parent(Left position,Top position,Right position,Bottom position)
                test.layout(test.getLeft(), curValue, test.getRight(), curValue + test.getHeight());
            }
        });

        //设置动画的监听器
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("------>", "animation start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("------>", "animation end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("------>", "animation cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("------>", "animation repeat");
            }
        });

        //重复方式与次数必须配合使用
//        animator.setRepeatMode(ValueAnimator.REVERSE);//设置重复的方式：setRepeatMode(int value)用于设置循环模式，取值为ValueAnimation.RESTART时,表示正序重新开始，当取值为ValueAnimation.REVERSE表示倒序重新开始。
//        animator.setRepeatCount(ValueAnimator.INFINITE);//设置重复的次数：setRepeatCount(int value)用于设置动画循环次数,设置为0表示不循环，设置为ValueAnimation.INFINITE表示无限循环。

        //使用插值器（加速器）
        animator.setInterpolator(new BounceInterpolator());//弹跳插值器
        animator.start();

        return animator;
    }

    /**
     * 改变颜色的动画
     *
     * @return
     */
    private ValueAnimator doColorAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0xffffff00, 0xff0000ff, 0xffffff00);
        animator.setEvaluator(new ArgbEvaluator());//设置Evaluator为颜色的Evaluator
        animator.setDuration(6000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                test.setBackgroundColor(curValue);
            }
        });

        animator.start();
        return animator;
    }

    /**
     * 改变的字符显示的动画
     *
     * @return
     */
    private ValueAnimator doCharAnim() {

        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(), Character.valueOf('A'), Character.valueOf('Z'));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char text = (char) animation.getAnimatedValue();
                test.setText(String.valueOf(text));
            }
        });
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());//设置加速器
        animator.start();

        return animator;
    }

    /**
     * 自定义缩放动画
     *
     * @return
     */
    private ValueAnimator doPointAnim() {
        return myPointView.doPointAnim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_anim:
                repeatAnimator = selectAnim();
                break;
            case R.id.btn_cancel_anim:
                if (repeatAnimator != null) {
                    repeatAnimator.cancel();
                }
                break;
            case R.id.btn_location_anim:
                animType = ANIM_TYPE_LOC;
                break;
            case R.id.btn_color_anim:
                animType = ANIM_TYPE_COL;
                break;
            case R.id.btn_char_anim:
                animType = ANIM_TYPE_CHA;
                break;
            case R.id.btn_point_anim:
                animType = ANIM_TYPE_POINT;
                break;
        }
    }
}
