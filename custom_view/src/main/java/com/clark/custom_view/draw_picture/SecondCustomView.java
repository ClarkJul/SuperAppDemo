package com.clark.custom_view.draw_picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class SecondCustomView extends View {
    private Context mContext;

    public SecondCustomView(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 自定义视图重绘的方法
     *
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
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度
//        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影


        //设置画布背景颜色
        canvas.drawRGB(255, 255, 255);//与canvas.drawColor()方法效果一样

        /**
         * 画直线
         * void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
         *  参数：startX:开始点X坐标
         startY:开始点Y坐标
         stopX:结束点X坐标
         stopY:结束点Y坐标
         *
         * 多条直线
         *  void drawLines (float[] pts, Paint paint)
         *  void drawLines (float[] pts, int offset, int count, Paint paint)
         *
         *  pts(x1,y1,x2,y2,x3,y3,x4,y4)连接点(x1,y1)(x2,y2),然后在连接(x3,y3)(x4,y4)
         */
        //画直线
        canvas.drawLine(0, 0, 100, 100, paint);
        float[] pts={100,0,200,100,250,50,300,100};
        canvas.drawLines(pts,paint);

        /**
         * void drawPoint (float x, float y, Paint paint)
         * 多点
         * void drawPoints (float[] pts, Paint paint)
         * void drawPoints (float[] pts, int offset, int count, Paint paint)
         */
        //画点
        canvas.drawPoint(350,50,paint);


        /**
         * 矩形:Rect和RectF区别不大
         * 四个矩形方法
         *  RectF()
            RectF(float left, float top, float right, float bottom)
            RectF(RectF r)
            RectF(Rect r)
         *
         * 三个画矩形的方法
         * void drawRect (float left, float top, float right, float bottom, Paint paint)
           void drawRect (RectF rect, Paint paint)
           void drawRect (Rect r, Paint paint)
         */

        canvas.drawRect(10,110,110,210,paint);

        /**
         * 画圆角矩形
         * void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
         *  RectF rect:要画的矩形
            float rx:生成圆角的椭圆的X轴半径
            float ry:生成圆角的椭圆的Y轴半径
         */
        RectF rectF=new RectF(120f,110f,220f,210f);
        canvas.drawRoundRect(rectF,20,20,paint);

        /**
         * 画椭圆
         * void drawOval (RectF oval, Paint paint)
         * RectF oval：用来生成椭圆的矩形
         */
        RectF rect=new RectF(230f,110f,380f,210f);
        canvas.drawOval(rect,paint);

        /**
         * 画圆弧
         * void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * 参数：
            RectF oval:生成椭圆的矩形
            float startAngle：弧开始的角度，以X轴正方向为0度
            float sweepAngle：弧持续的角度
            boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
         */
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);//抗锯齿功能
        paint1.setColor(Color.RED);  //设置画笔颜色
        paint1.setStyle(Paint.Style.STROKE);//设置填充样式
        paint1.setStrokeWidth(10);//设置画笔宽度

        RectF rect1=new RectF(10,250f,150f,350f);
        canvas.drawArc(rect1,100,-270,false,paint1);
    }
}
