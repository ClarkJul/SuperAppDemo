package com.clark.custom_view.draw_picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomEditView extends View{
    private Path mPath = new Path();
    private float mPreX,mPreY;
    public CustomEditView(Context context) {
        super(context);
    }

    public CustomEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                //line实现形式
//                mPath.moveTo(event.getX(), event.getY());
//                return true;

                //贝塞尔曲线实现形式
                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
//                mPath.lineTo(event.getX(), event.getY());
//                postInvalidate();//在主线程和子线程都能重绘的方法，速度稍慢

                //贝塞尔曲线实现形式
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                invalidate();//只能在主线程重绘的方法
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);

        canvas.drawPath(mPath,paint);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}
