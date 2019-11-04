package com.android.clark.superappdemo.banner.custom_banner;

import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class SecondTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage( View page, float position) {
        float MIN_SCALE=0.75f;
        float scaleFactor=MIN_SCALE+(1-MIN_SCALE)*(1-Math.abs(position));
        if (position>1||position<-1){
            page.setAlpha(0f);
        }else if (position<=0){
            page.setAlpha(1+position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }else if (position<=1){
            page.setAlpha(1-position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
    }

}
