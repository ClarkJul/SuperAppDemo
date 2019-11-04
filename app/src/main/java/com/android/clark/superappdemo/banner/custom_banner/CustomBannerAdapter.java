package com.android.clark.superappdemo.banner.custom_banner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.viewpager.widget.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomBannerAdapter extends PagerAdapter {
    private List<Uri> images;
    private Context context;
    private int mSize;

    public CustomBannerAdapter(List<Uri> images, Context context) {
        this.images = images;
        this.context = context;
        mSize = images.size();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int newposition = position % mSize;
        ImageView imageView = new ImageView(context);
        Glide.with(context).load(images.get(newposition)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,newposition+"号页面被点击了",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,BannerTestActivity.class);
                context.startActivity(intent);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((View) object);
    }
}
