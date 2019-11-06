package com.clark.download.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.clark.download.R;

public class ProgressButton extends View {

    private String mText = null;
    private int mMax = -1;
    private int mProgress = -1;
    private boolean mIsInProgress = false;
    private Drawable mProgressBGDrawable = null;//进度条背景
    private Drawable mProgressFGDrawable = null;//进度条前景
    private Drawable mInitBGDrawable = null;//安装中，等待中也是这个背景
    private Drawable mOpenBGDrawable = null;

    private TextPaint mPaint = null;
    private Rect mRect = new Rect();

    private int mInitTextColor;
    private int mOpenTextColor;
    private int mProgressTextColor;
    private int mDisableTextColor;

    private int mBorderWidth;

    public ProgressButton(Context context) {
        this(context, null, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Resources res = getContext().getResources();
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.InstallButton);

        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Align.CENTER);

        mInitTextColor = ta.getColor(R.styleable.InstallButton_initTextColor, res.getColor(R.color.c4586e2));
        mOpenTextColor = ta.getColor(R.styleable.InstallButton_progressTextColor, res.getColor(R.color.c212121));
        mProgressTextColor = mOpenTextColor;
        mDisableTextColor = ta.getColor(R.styleable.InstallButton_disableTextColor, res.getColor(R.color.c80212121));

        mBorderWidth = ta.getDimensionPixelSize(R.styleable.InstallButton_borderWidth, 0);

        mInitBGDrawable = ta.getDrawable(R.styleable.InstallButton_initBackground);
        mProgressFGDrawable = ta.getDrawable(R.styleable.InstallButton_progressForeground);
        mProgressBGDrawable = ta.getDrawable(R.styleable.InstallButton_progressBackground);
        mOpenBGDrawable = ta.getDrawable(R.styleable.InstallButton_progressOpenBackground);

        if (mProgressFGDrawable == null) {
            mProgressFGDrawable = res.getDrawable(R.drawable.progress_btn_blue_bg);
        }
        if (mProgressBGDrawable == null) {
            mProgressBGDrawable = res.getDrawable(R.drawable.progress_btn_white_bg);
        }

        if (mOpenBGDrawable == null) {
            mOpenBGDrawable = res.getDrawable(R.drawable.progress_btn_gray_bg);
        }

        float textsize = ta.getDimensionPixelSize(R.styleable.InstallButton_textSize, 18);
        ta.recycle();

        mPaint.setTextSize(textsize);
        mPaint.setColor(mInitTextColor);

        setBackground(mInitBGDrawable);
        mText = "安装";
    }

    public void setText(String text) {
        mText = text;
        if (text.equals("打开")) {
            mPaint.setColor(mOpenTextColor);
            setBackground(mOpenBGDrawable);
        } else if (text.equals("安装中")) {
            mPaint.setColor(mDisableTextColor);
            setBackground(mOpenBGDrawable);
        } else {
            if (!mIsInProgress) {
                mPaint.setColor(mInitTextColor);
                setBackground(mInitBGDrawable);
            } else {
                setBackground(null);
            }
        }
        postInvalidate();
    }

    public void setText(int id) {
        String text = null;
        try {
            text = getContext().getResources().getString(id);
        } catch (Exception e) {
        }
        setText(text);
    }

    public void setProgress(int progress) {
        if (mProgress != progress) {
            if (progress < 0) {
                progress = 0;
            } else if (progress > mMax) {
                progress = mMax;
            }

            mProgress = progress;
            postInvalidate();
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public void setMax(int max) {
        if (max < 0) {
            return;
        }

        if (mMax != max) {
            mMax = max;
            initProgressState();
        }
    }

    public void removeProgressState() {
        mMax = -1;
        mProgress = -1;

        if (mIsInProgress) {
            mIsInProgress = false;

            mPaint.setColor(mInitTextColor);
            setBackground(mInitBGDrawable);
        }
    }

    private void initProgressState() {
        if (!mIsInProgress) {
            mIsInProgress = true;
            mPaint.setColor(mProgressTextColor);
            setBackground(null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        if (mIsInProgress && mProgressBGDrawable != null) {
            mRect.set(0, 0, width, height);
            mProgressBGDrawable.setBounds(mRect);
            mProgressBGDrawable.draw(canvas);
        }

        if (mIsInProgress && mProgressFGDrawable != null) {
            int pw = (width - mBorderWidth * 2) * mProgress / mMax;
            if (pw > -1) {
                mRect.set(mBorderWidth, 0, pw, height);
                mProgressFGDrawable.setBounds(mRect);
                mProgressFGDrawable.draw(canvas);
            }
        }
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            float x = ((float) width) / 2;
            float y = (height - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(mText, x, y, mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeProgressState();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void applyBackgroundAndTextColor(Drawable background, int textColor, int progressColor, int openTextColor, Drawable progressFG, Drawable progressBG) {
        if (background != null) {
            mInitBGDrawable = background;
            mOpenBGDrawable = background;
        }
        if (progressFG != null) {
            mProgressFGDrawable = progressFG;
        }
        if (progressBG != null) {
            mProgressBGDrawable = progressBG;
        }
        mInitTextColor = textColor;
        mOpenTextColor = openTextColor;
        mProgressTextColor = progressColor;
        postInvalidate();
    }
}
