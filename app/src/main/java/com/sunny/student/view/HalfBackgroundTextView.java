package com.sunny.student.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.sunny.student.R;

public class HalfBackgroundTextView extends AppCompatTextView {


    private Paint paint;

    private int color;
    public HalfBackgroundTextView(Context context) {
        this(context,  null);
    }

    public HalfBackgroundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfBackgroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HalfBackgroundTextView);
        color = array.getColor(R.styleable.HalfBackgroundTextView_backgroundColor, 0);
        paint = new Paint();
        paint.setColor(color);
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("hhhh", "top" +getTop() + "bottom" + getBottom() + "right" + getRight() + "left" + getLeft() + "height"
                + getMeasuredHeight() + "width" + getMeasuredWidth());
        canvas.drawRect(0, getMeasuredHeight() / 2, getMeasuredWidth(), getMeasuredHeight(), paint);
        super.onDraw(canvas);
    }
}
