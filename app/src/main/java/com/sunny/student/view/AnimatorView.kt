package com.sunny.student.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Button

/**
 * Created by SunShuo.
 * Date: 2019-10-23
 * Time: 20:27
 */
class AnimatorView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null
,defdefStyleAttr: Int = 0) : Button(context, attributeSet, defdefStyleAttr){

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        Log.d("test-layout", "hhh")
    }

}