package com.sunny.student.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sunny.student.R;

/**
 * created by sunshuo
 * on 2020/9/18
 */
public class CustomCardView extends ConstraintLayout {

    private int mCardCornerRadius;
    private
    public CustomCardView(Context context) {
        this(context, null);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedValue = context.obtainStyledAttributes(attributeSet, R.styleable.CustomCardView);
        mCardCornerRadius = typedValue.getDimensionPixelSize(R.styleable.CustomCardView_cardCornerRadius, 0);

        setBackground(createBackground(getContext(),0, getResources().getColor(R.color.ff6c00)
                , 30 , 10));
    }

    private RoundRectDrawableWithShadow createBackground(Context context, int backgroundColor, int shadowColor, float radius, float elevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)), radius, elevation,
                elevation);
    }
}
