package com.sunny.student.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sunny.student.R;


/**
 * 带阴影的ConstraintLayout
 * 替换ShadowLayout, 降低层级
 * created by sunshuo
 * on 2020-05-19
 */
public class ConstraintShadowLayout extends ConstraintLayout {
    private int mShadowColor;
    private int mFillColor;
    private float mShadowRadius;
    private float mCornerRadius;
    private boolean asideValid;           //单边有效 针对dy大于0时
    private float mDx;
    private float mDy;

    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;

    public ConstraintShadowLayout(Context context) {
        this(context, null);
    }

    public ConstraintShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConstraintShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mForceInvalidateShadow) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    public void setInvalidateShadowOnSizeChanged(boolean invalidateShadowOnSizeChanged) {
        mInvalidateShadowOnSizeChanged = invalidateShadowOnSizeChanged;
    }

    public void invalidateShadow() {
        mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);

        int xPadding = (int) (mShadowRadius + Math.abs(mDx));
        int yPadding = (int) (mShadowRadius + Math.abs(mDy));
        setPadding(xPadding, (asideValid && mDy > 0) ? 0 : yPadding, xPadding, yPadding);
    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowRadius, mDx, mDy, mShadowColor, mFillColor);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        setBackground(drawable);
    }


    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.ConstraintShadowLayout);
        if (attr == null) {
            return;
        }

        try {
            mCornerRadius = attr.getDimension(R.styleable.ConstraintShadowLayout_cornerRadiusC, 4);
            mShadowRadius = attr.getDimension(R.styleable.ConstraintShadowLayout_shadowRadiusC, 4);
            mDx = attr.getDimension(R.styleable.ConstraintShadowLayout_dxC, 0);
            mDy = attr.getDimension(R.styleable.ConstraintShadowLayout_dyC, 0);
            asideValid = attr.getBoolean(R.styleable.ConstraintShadowLayout_asideValidC, false);
            mShadowColor = attr.getColor(R.styleable.ConstraintShadowLayout_shadowColorC, Color.TRANSPARENT);
            mFillColor = attr.getColor(R.styleable.ConstraintShadowLayout_fillColorC, Color.WHITE);
        } finally {
            attr.recycle();
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

        if (dy > 0) {
            shadowRect.top += dy;
            shadowRect.bottom -= dy;
        } else if (dy < 0) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
        }

        if (dx > 0) {
            shadowRect.left += dx;
            shadowRect.right -= dx;
        } else if (dx < 0) {
            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        }

        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(fillColor);
        shadowPaint.setStyle(Paint.Style.FILL);

        //再画背景之圆角


        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);

        return output;
    }
    public void setmShadowColor(int colorId){
        mShadowColor=getResources().getColor(colorId);
    }

    public void setmFillColor(int mFillColor) {
        this.mFillColor = mFillColor;
    }

    public void setmShadowRadius(float mShadowRadius) {
        this.mShadowRadius = mShadowRadius;
    }

    public void setmCornerRadius(float mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
    }

    public void setAsideValid(boolean asideValid) {
        this.asideValid = asideValid;
    }

    public void setmDx(float mDx) {
        this.mDx = mDx;
    }

    public void setmDy(float mDy) {
        this.mDy = mDy;
    }
}
