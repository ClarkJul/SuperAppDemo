package com.android.clark.superappdemo.custom_control.draw_picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class ThirdCustomView extends View {
    private Context mContext;

    public ThirdCustomView(Context context) {
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

        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(3);//设置画笔宽

        //设置画布背景颜色
        canvas.drawRGB(255, 255,255);//与canvas.drawColor()方法效果一样

        //直线路径
        Path path = new Path();
        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(300, 100);//画第二条直线
        path.lineTo(380, 80);//第三条直线
        path.close();//闭环

        canvas.drawPath(path, paint);


        /**矩形路径
         * void addRect (float left, float top, float right, float bottom, Path.Direction dir)
         * void addRect (RectF rect, Path.Direction dir)
         * Path.Direction有两个值：
         *      Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
         *      Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
         */
        //先创建两个大小一样的路径
        // 第一个逆向生成
        Path CCWRectpath = new Path();
        RectF rect1 =  new RectF(50, 150, 180, 250);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);
        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 =  new RectF(200, 150, 390, 250);
        CWRectpath.addRect(rect2, Path.Direction.CW);
        //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);

        //依据路径写出文字
        String text="123456789";
        paint.setColor(Color.GREEN);
        paint.setTextSize(20);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 25, paint);//逆时针生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 25, paint);//顺时针生成

        /**
         * 圆角矩形路径
         * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
         * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
         */
        paint.setColor(Color.RED);
        Path pathY = new Path();
        RectF rectY1 =  new RectF(20, 280, 180, 380);
        pathY.addRoundRect(rectY1, 10, 15 ,Path.Direction.CCW);

        RectF rectY2 =  new RectF(200, 280, 350, 380);
        float radii[] ={10,15,20,25,30,35,40,45};
        pathY.addRoundRect(rectY2, radii, Path.Direction.CCW);

        canvas.drawPath(pathY, paint);

        /**
         * 圆形路径
         * void addCircle (float x, float y, float radius, Path.Direction dir)
         */

        Path pathY1 = new Path();
        pathY1.addCircle(550, 200, 100, Path.Direction.CCW);
        canvas.drawPath(pathY1, paint);

        /**椭圆路径
         * void addOval (RectF oval, Path.Direction dir)
         */

        /**
         * 弧形路径
         * void addArc (RectF oval, float startAngle, float sweepAngle)
         */
    }
}
