package com.sunny.student.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sunny.student.R;

/**
 * created by sunshuo
 * on 2020/9/18
 */
public class CustomCardView extends ConstraintLayout {

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
        int cardCornerRadius = typedValue.getDimensionPixelSize(R.styleable.CustomCardView_cardCornerRadius, 4);
        int cardShadowColor = typedValue.getColor(R.styleable.CustomCardView_cardShadowColor, Color.TRANSPARENT);
        int cardDy = typedValue.getDimensionPixelSize(R.styleable.CustomCardView_cardDy, 4);
        int cardShadowSize = typedValue.getDimensionPixelSize(R.styleable.CustomCardView_cardShadowSize, 4);
        ColorStateList backgroundColor = typedValue.getColorStateList(R.styleable.CustomCardView_cardBackgroundColor);
        setBackground(createBackground(getContext(), backgroundColor, cardShadowColor
                , cardCornerRadius , cardDy));
        typedValue.recycle();
    }

    private RoundRectDrawableWithShadow createBackground(Context context, ColorStateList backgroundColor, int shadowColor, float radius, float elevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, shadowColor, radius, elevation,
                elevation);
    }
}
