package com.sunny.student.overlapopt

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by sunshuo.man on 2023/2/1
 */
class ForceOverLapView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    View(context, attrs, defStyleAttr) {
    override fun hasOverlappingRendering(): Boolean {
        return true
    }
}