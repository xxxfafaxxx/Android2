package com.example.zpi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerticalSeekBar_Reverse extends SeekBar{

    private OnSeekBarChangeListener myListener;

    public VerticalSeekBar_Reverse(Context context) {
        super(context);
    }

    public VerticalSeekBar_Reverse(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar_Reverse(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener){
        this.myListener = mListener;
    }
    protected void onDraw(Canvas c) {
        c.rotate(90);
        c.translate(0, -getWidth());

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                myListener.onStartTrackingTouch(this);
                break;
            case MotionEvent.ACTION_MOVE:
                int i=0;
                Log.d("max",""+getMax());
                Log.d("getY",""+event.getY());
                Log.d("height",""+getHeight());
                float pom=event.getY();
                if (pom>=getHeight())
                    pom=getHeight();
                if(pom<=-3)
                    pom=-3;
                i=getMax() - (int) (getMax() * pom / getHeight());
                Log.d("i",""+i);
                setProgress(100-i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                myListener.onProgressChanged(this,100-i,true);
                break;
            case MotionEvent.ACTION_UP:
                myListener.onStopTrackingTouch(this);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    public void klik(){
    	onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

}