package com.clark.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

    public static Bitmap decodeSampledBitmapFromResources(Resources resources,int resId,int reqWidth, int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();

        //inJustDecodeBounds设置为true时，BitmapFactory只会解析图片的原始高宽信息，并不会真正加载图片，这个操作是轻量级的
        //获取的图片的高宽信息和图片的位置及运行的设备有关，如同一张图片在不同的drawable文件夹下高宽不同
        options.inJustDecodeBounds=true;

        BitmapFactory.decodeResource(resources,resId,options);

        //计算采样率
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;

        //加载适合大小的图片
        return BitmapFactory.decodeResource(resources,resId,options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
