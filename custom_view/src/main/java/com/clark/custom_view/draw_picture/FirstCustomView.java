package com.clark.custom_view.draw_picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class FirstCustomView extends View {
    private Context mContext;
    public FirstCustomView(Context context) {
        super(context);
        mContext=context;
    }

    /**
     * 自定义视图重绘的方法
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Paint的基本设置函数：
           paint.setAntiAlias(true);//抗锯齿功能
           paint.setColor(Color.RED);  //设置画笔颜色    
           paint.setStyle(Paint.Style style);//设置填充样式
                Paint.Style.FILL:填充内部
                Paint.Style.FILL_AND_STROKE:填充内部和描边
                Paint.Style.STROKE:仅描边
           paint.setStrokeWidth(30);//设置画笔宽度
           paint.setShadowLayer(float radius, float dx, float dy, int color);//设置阴影
                radius:阴影的倾斜度
                dx:水平位移
                dy:垂直位移
         */
        //设置画笔基本属性
        Paint paint=new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影


        //设置画布背景颜色
        canvas.drawRGB(255, 255,255);//与canvas.drawColor()方法效果一样

        //画圆
        canvas.drawCircle(190, 200, 150, paint);
    }
}
