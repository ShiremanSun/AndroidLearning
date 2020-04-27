package com.sunny.student.customspan

import android.graphics.*
import android.text.style.ReplacementSpan

/**
 * created by sunshuo
 * on 2020-01-07
 */
class RoundTagSpan : ReplacementSpan() {
    public var mStroke = 0
    public var mBackground = Color.WHITE
    public var mPaddingLeft = 0
    public var mPaddingRight = 0
    public var mMarginTop = 0
    public var mColor = Color.BLUE
    private var mWidth = 0
    private var mCorner = 5.0F

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val rect = Rect()
        paint.getTextBounds(text.toString(), start, end, rect)
        mWidth = (rect.width() + mPaddingLeft + mPaddingRight + mStroke * 2 + mCorner * 2).toInt()
        return mWidth
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val backgroundPaint = Paint()
        backgroundPaint.color = mColor
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.isAntiAlias = true
        val rect = RectF(x, top.toFloat() , x + mWidth , bottom.toFloat())
        canvas.drawRoundRect(rect, 10F, 10F, backgroundPaint)
        if (text != null) {
            canvas.drawText(text, start, end, x + mCorner / 2, y.toFloat(), paint)
        }
    }


}