package com.sunny.student.fragment

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by sunshuo.man on 2023/5/23
 * @author sunshuo.man@bytedance.com
 */
class TestConstraintLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}