package com.sunny.student.fresco

import android.graphics.drawable.Animatable

interface ImageLoadListener {
    fun onLoadStarted()

    fun onLoadSuccess(imageUrl: String?, width: Int, height: Int)

    fun onLoadFailed(imageUrl: String?, exception: Exception?)

    fun handleAnimatable(animatable: Animatable)
}