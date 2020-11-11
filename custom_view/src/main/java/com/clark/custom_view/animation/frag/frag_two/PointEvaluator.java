package com.clark.custom_view.animation.frag.frag_two;

import android.animation.TypeEvaluator;

class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int start = startValue.getRadius();
        int end = endValue.getRadius();
        int curValue = (int) (start + fraction * (end - start));
        return new Point(curValue);
    }
}
