package com.rpjuvvadi.calendardialogfragmentexample.utilities;

/**
 * Created by rpj on 8/2/16.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rpjuvvadi.calendardialogfragmentexample.R;

/**
 * Idea of drawing a canvas Circle behind TextView and its code
 * is Copied from StackOverflow on 5/11/16.
 * http://stackoverflow.com/a/34685568
 */
public class CalendarDateTextView extends TextView {

    public static int DISABLED          = 0;
    public static int AVAILABLE         = 1;
    public static int CLOSED            = 2;
    public static int BOOKED            = 3;
    public static int REQUESTED         = 4;

    private int textColor;
    private int backgroundColor;
    private int borderColor;

    public CalendarDateTextView(Context context) {
        super(context);
    }

    public CalendarDateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarDateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {

        // Set Background Color
        Paint circlePaint = new Paint();
        circlePaint.setColor(backgroundColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Set Stroke Color
        Paint strokePaint = new Paint();
        strokePaint.setColor(borderColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Set Text Color
        setTextColor(textColor);
        int  h = this.getHeight();
        int  w = this.getWidth();
        int diameter = ((h > w) ? h : w);
        int radius = diameter/2;

        this.setHeight(diameter);
        this.setWidth(diameter);

        // Draw Border
        canvas.drawCircle(diameter / 2 , diameter / 2, radius, strokePaint);
        // Draw Circle
        canvas.drawCircle(diameter / 2, diameter / 2, radius-2, circlePaint);

        super.draw(canvas);
    }

    private int getColor(int resId) {
        if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            return getContext().getResources().getColor(resId, null);
        } else {
            return getContext().getResources().getColor(resId);
        }
    }

    public void setTextViewType(int type) {

        if ( type == DISABLED ) {
            this.textColor = getColor(R.color.colorGreyLight);
            this.backgroundColor = getColor(R.color.colorWhite);
            this.borderColor = getColor(R.color.colorGreyLight);
            invalidate();
            return;
        }
        if ( type == AVAILABLE ) {
            this.textColor = getColor(R.color.colorTurquoise);
            this.backgroundColor = getColor(R.color.colorWhite);
            this.borderColor =  getColor(R.color.colorTurquoise);
            invalidate();
            return;
        }
        if ( type == CLOSED) {
            this.textColor = getColor(R.color.colorWhite);
            this.backgroundColor = getColor(R.color.colorGrey);
            this.borderColor =  getColor(R.color.colorGrey);
            invalidate();
            return;
        }
        if ( type == BOOKED ) {
            this.textColor = getColor(R.color.colorWhite);
            this.backgroundColor = getColor(R.color.colorTurquoise);
            this.borderColor =  getColor(R.color.colorTurquoise);
            invalidate();
            return;
        }
        if ( type == REQUESTED ) {
            this.textColor = getColor(R.color.colorWhite);
            this.backgroundColor = getColor(R.color.colorMajenta);
            this.borderColor =  getColor(R.color.colorMajenta);
            invalidate();
            return;
        }

    }
}